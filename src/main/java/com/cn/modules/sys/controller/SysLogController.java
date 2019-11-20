package com.cn.modules.sys.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.R;
import com.cn.modules.sys.entity.BatchAttachEntity;
import com.cn.modules.sys.entity.SysLogEntity;
import com.cn.modules.sys.service.SysLogService;
import com.cn.modules.sys.utils.AttachUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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
* 日志控制器
*
* @author zhangheng
* @date 2019-04-28 13:47:05
*/
@RestController
@RequestMapping("/syslog")
@Slf4j
public class SysLogController extends AbstractController {

    @Autowired
    private SysLogService sysLogService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<SysLogEntity> page = new PageFactory<SysLogEntity>().defaultPage();
        page.setDesc("CREATE_DATE");
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysLogEntity> entityQueryWrapper = (new CustomWrapper(SysLogEntity.class)).parseSqlWhere(list);

        page.setRecords( sysLogService.selectPage(page,entityQueryWrapper));

        return R.ok().put("page", new PageUtils(page));
    }

    /**
     * 获取分页信息
     * @param json 对象
     * @return Object 分页信息
     */
    @GetMapping(value = "/listByUser")
    public Object listByUser(String json) {

        Page<SysLogEntity> page = new PageFactory<SysLogEntity>().defaultPage();
        page.setDesc("CREATE_DATE");
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysLogEntity> entityQueryWrapper = (new CustomWrapper(SysLogEntity.class)).parseSqlWhere(list);

        entityQueryWrapper.eq("USERNAME",getUser().getUsername());
        page.setRecords( sysLogService.selectPage(page,entityQueryWrapper));
        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("syslog:add")
    public Object add(@RequestBody SysLogEntity entity) {
        sysLogService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("syslog:delete")
    public Object delete(@RequestBody Long[] ids) {
        sysLogService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SysLogEntity entity) {
        sysLogService.updateById(entity);
        return R.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(SysLogEntity entity) {
        return R.ok().put("syslog", sysLogService.selectOne(entity));
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
        QueryWrapper<SysLogEntity> entityQueryWrapper = (new CustomWrapper(SysLogEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = sysLogService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<SysLogEntity> page = new Page<SysLogEntity>(i+1, EXPORT_MAX);
                    List<SysLogEntity> result = sysLogService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, SysLogEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<SysLogEntity> page = new Page<SysLogEntity>(1, EXPORT_MAX);
                List<SysLogEntity> result = sysLogService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams, SysLogEntity.class, result);
                }
                String filename = "日志("+DateUtil.today()+")";
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