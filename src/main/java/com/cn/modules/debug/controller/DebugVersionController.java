package com.cn.modules.debug.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.R;
import com.cn.modules.debug.entity.DebugVersionEntity;
import com.cn.modules.debug.service.DebugVersionService;
import com.cn.modules.sys.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
* 版本迭代控制器
*
* @author zhangheng
* @date 2019-05-25 23:31:11
*/
@RestController
@RequestMapping("/debugversion")
@Slf4j
public class DebugVersionController extends AbstractController {

    @Autowired
    private DebugVersionService debugVersionService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<DebugVersionEntity> page = new PageFactory<DebugVersionEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<DebugVersionEntity> entityQueryWrapper = (new CustomWrapper(DebugVersionEntity.class)).parseSqlWhere(list);
        page.setRecords( debugVersionService.selectPage(page,entityQueryWrapper));

        return R.ok().put("page", new PageUtils(page));
    }


    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("debugversion:add")
    public Object add(@RequestBody DebugVersionEntity entity) {
        debugVersionService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("debugversion:delete")
    public Object delete(@RequestBody Integer[] ids) {
        debugVersionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody DebugVersionEntity entity) {
        debugVersionService.updateById(entity);
        return R.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(DebugVersionEntity entity) {
        return R.ok().put("debugversion", debugVersionService.selectOne(entity));
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
        QueryWrapper<DebugVersionEntity> entityQueryWrapper = (new CustomWrapper(DebugVersionEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = debugVersionService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<DebugVersionEntity> page = new Page<DebugVersionEntity>(i+1, EXPORT_MAX);
                    List<DebugVersionEntity> result = debugVersionService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, DebugVersionEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<DebugVersionEntity> page = new Page<DebugVersionEntity>(1, EXPORT_MAX);
                List<DebugVersionEntity> result = debugVersionService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,DebugVersionEntity.class, result);
                }
                String filename = "版本迭代("+DateUtil.today()+")";
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