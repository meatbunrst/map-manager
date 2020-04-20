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
import com.cn.common.utils.Result;
import com.cn.modules.sys.entity.BatchAttachEntity;
import com.cn.modules.sys.entity.SysDictDetailedEntity;
import com.cn.modules.sys.entity.SysDictEntity;
import com.cn.modules.sys.service.SysDictDetailedService;
import com.cn.modules.sys.service.SysDictService;
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
* 字典表控制器
*
* @author zhangheng
* @date 2019-08-30 17:37:39
*/
@RestController
@RequestMapping("/sysdict")
@Slf4j
public class SysDictController extends AbstractController {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysDictDetailedService sysDictDetailedService;
    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<SysDictEntity> page = new PageFactory<SysDictEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysDictEntity> entityQueryWrapper = (new CustomWrapper(SysDictEntity.class)).parseSqlWhere(list);
        page.setRecords( sysDictService.selectPage(page,entityQueryWrapper));

        return Result.ok().put("page", new PageUtils(page));
    }

    /**
    * 获取分页信息
    * @param entity 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/listModel")
    public Object list(SysDictEntity entity) {
    Page<SysDictEntity> page = new PageFactory<SysDictEntity>().defaultPage();
        page.setRecords( sysDictService.selectPage(page,entity));
        return Result.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("sysdict:add")
    public Object add(@RequestBody SysDictEntity entity) {
        sysDictService.save(entity);
        return Result.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("sysdict:delete")
    public Object delete(@RequestBody String[] ids) {
        sysDictService.removeByIds(Arrays.asList(ids));

        for (String id : ids) {
            SysDictDetailedEntity entity = new SysDictDetailedEntity();
            entity.setLabelId(id);
            sysDictDetailedService.deleteByModel(entity);
        }
        return Result.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SysDictEntity entity) {
        sysDictService.updateById(entity);
        return Result.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(SysDictEntity entity) {
        return Result.ok().put("sysdict", sysDictService.selectOne(entity));
    }

    /**
    *  基本附件表导入
    * @param file  上传的文件
    * @return
    */
    @RequestMapping(value="/import")
    public Object  importFile(@RequestParam("file")MultipartFile file){

        boolean flag = true;
        BatchAttachEntity model = new BatchAttachEntity();
        try {
            InputStream inputStream = file.getInputStream();
            ImportParams params = new ImportParams();
            params.setNeedVerfiy(true);
            params.setTitleRows(0);
            /* params.setVerifyHandler(new ExcelVerifyHandlerImpl());
            * 自定义的验证
            * */
            ExcelImportResult<SysDictEntity> result = ExcelImportUtil.importExcelMore(
                    inputStream,SysDictEntity.class, params);
            if (result.isVerfiyFail()){
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < result.getFailList().size(); i++) {
                    buffer.append(ReflectionToStringBuilder.toString(result.getFailList().get(i)));
                }
                String fileName = "导入失败"+ DateUtil.format(new Date(),PURE_DATETIME_PATTERN);
                //保存失败文件到临时目录
                model = AttachUtils.saveTmpFile(result.getFailWorkbook(),fileName);
            } else{
                //	成功的时候
                sysDictService.saveBatch(result.getList());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (flag){
            return Result.ok();
        }else {
            return Result.error("导入错误").put("batch",model);
        }
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
        QueryWrapper<SysDictEntity> entityQueryWrapper = (new CustomWrapper(SysDictEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = sysDictService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<SysDictEntity> page = new Page<SysDictEntity>(i+1, EXPORT_MAX);
                    List<SysDictEntity> result = sysDictService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, SysDictEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<SysDictEntity> page = new Page<SysDictEntity>(1, EXPORT_MAX);
                List<SysDictEntity> result = sysDictService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,SysDictEntity.class, result);
                }
                String filename = "字典表("+DateUtil.today()+")";
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