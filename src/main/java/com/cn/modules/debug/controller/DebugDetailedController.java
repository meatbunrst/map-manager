package com.cn.modules.debug.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.R;
import com.cn.modules.debug.entity.DebugDetailedEntity;
import com.cn.modules.debug.service.DebugDetailedService;
import com.cn.modules.sys.controller.AbstractController;
import com.cn.modules.sys.entity.BatchAttachEntity;
import com.cn.modules.sys.utils.AttachUtils;
import com.cn.modules.sys.utils.OaTaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
/**
* bug详情控制器
*
* @author zhangheng
* @date 2019-05-25 23:36:55
*/
@RestController
@RequestMapping("/debugdetailed")
@Slf4j
public class DebugDetailedController extends AbstractController {

    @Autowired
    private DebugDetailedService debugDetailedService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<DebugDetailedEntity> page = new PageFactory<DebugDetailedEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<DebugDetailedEntity> entityQueryWrapper = (new CustomWrapper(DebugDetailedEntity.class)).parseSqlWhere(list);
        List<DebugDetailedEntity> entities = debugDetailedService.selectPage(page,entityQueryWrapper);
        entities.forEach(debugDetailedEntity -> {
            Date date = new Date();
            Date endDate = DateUtil.endOfDay(debugDetailedEntity.getEndDate()).toJdkDate();
            Date  warnDate = DateUtil.offsetDay(endDate, -2).toJdkDate();
            if (endDate.before(date)){
                debugDetailedEntity.setColorType("2");
            } else if (endDate.after(date) &&  warnDate.before(date)){
                debugDetailedEntity.setColorType("1");
            }else {
                debugDetailedEntity.setColorType("0");
            }
        });

        page.setRecords(entities );
        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("debugdetailed:add")
    public Object add(@RequestBody DebugDetailedEntity entity) {
        entity.setCreateDate(new Date());
        entity.setContent(StringEscapeUtils.unescapeHtml4(entity.getContent()));
        entity.setCreateBy(getUser().getAccountName());
        debugDetailedService.save(entity);
        OaTaskUtils.saveUndoTask(entity.getId().toString(),entity.getTitle(),entity.getUserId(),1);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("debugdetailed:delete")
    public Object delete(@RequestBody Integer[] ids) {
        debugDetailedService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 确定解决
     * @param entity 对象
     * @return Object 执行结果
     */
    @PostMapping(value = "/sovle")
    public Object sovle(@RequestBody DebugDetailedEntity entity) {
        OaTaskUtils.updateStatusByProjectId(entity.getId().toString());
        entity.setType(2);
        entity.setSolvePerson(getUser().getAccountName());
        entity.setSolveTime(new Date());
        debugDetailedService.updateById(entity);
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody DebugDetailedEntity entity) {
        debugDetailedService.updateById(entity);
        return R.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(DebugDetailedEntity entity) {
        entity = debugDetailedService.getOne(entity);
        Date date = new Date();

        Date endDate = DateUtil.endOfDay(entity.getEndDate()).toJdkDate();
        Date  warnDate = DateUtil.offsetDay(endDate, -2).toJdkDate();

        if (endDate.before(date)){
            entity.setColorType("2");
            entity.setWarnString("已经过期");
        } else if (endDate.after(date) &&  warnDate.before(date)){
            entity.setColorType("1");
            long hour = DateUtil.between(date,endDate, DateUnit.HOUR);
            entity.setWarnString("还有"+hour+"小时");
        }else {
            entity.setColorType("0");
            long day = DateUtil.betweenDay(date,endDate, true);
            entity.setWarnString("还有"+day+"天");
        }
        return R.ok().put("debugdetailed", entity);
    }
    /**
    * 导出Excel文件
    * @param request http请求
    * @param response http返回
    * @param json 对象
    */
    @GetMapping(value="/export")
    public void export(HttpServletRequest request, HttpServletResponse response,String json){
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<DebugDetailedEntity> entityQueryWrapper = (new CustomWrapper(DebugDetailedEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = debugDetailedService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<DebugDetailedEntity> page = new Page<DebugDetailedEntity>(i+1, EXPORT_MAX);
                    List<DebugDetailedEntity> result = debugDetailedService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, DebugDetailedEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<DebugDetailedEntity> page = new Page<DebugDetailedEntity>(1, EXPORT_MAX);
                List<DebugDetailedEntity> result = debugDetailedService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,DebugDetailedEntity.class, result);
                }
                String filename = "bug详情("+DateUtil.today()+")";
                renderExcel(request,response,filename,workbook);
        } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write(e.getMessage());
            } catch (Exception e2) {

            }
        }
  }
}