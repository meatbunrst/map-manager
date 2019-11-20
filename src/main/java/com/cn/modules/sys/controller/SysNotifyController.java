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
import com.cn.modules.sys.entity.SysNotifyEntity;
import com.cn.modules.sys.service.SysNotifyService;
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
* 通知通告控制器
*
* @author zhangheng
* @date 2019-08-05 22:52:03
*/
@RestController
@RequestMapping("/sysnotify")
@Slf4j
public class SysNotifyController extends AbstractController {

    @Autowired
    private SysNotifyService sysNotifyService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {
        Page<SysNotifyEntity> page = new PageFactory<SysNotifyEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysNotifyEntity> entityQueryWrapper = (new CustomWrapper(SysNotifyEntity.class)).parseSqlWhere(list);
        page.setRecords( sysNotifyService.selectPage(page,entityQueryWrapper));

        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 获取分页信息
    * @param entity 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/listModel")
    public Object list(SysNotifyEntity entity) {
    Page<SysNotifyEntity> page = new PageFactory<SysNotifyEntity>().defaultPage();
        page.setRecords( sysNotifyService.selectPage(page,entity));
        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("sysnotify:add")
    public Object add(@RequestBody SysNotifyEntity entity) {
        sysNotifyService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("sysnotify:delete")
    public Object delete(@RequestBody String[] ids) {
        sysNotifyService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 删除多个
     * @param ids 主键
     * @return Object 执行结果
     */
    @PostMapping(value = "/test")
    public Object test(@RequestParam(value="ids", required=false)String ids) {
        System.err.println(ids);
        return R.ok();
    }
    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SysNotifyEntity entity) {
        sysNotifyService.updateById(entity);
        return R.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(SysNotifyEntity entity) {
        return R.ok().put("sysnotify", sysNotifyService.selectOne(entity));
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
            ExcelImportResult<SysNotifyEntity> result = ExcelImportUtil.importExcelMore(
                    inputStream,SysNotifyEntity.class, params);
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
                sysNotifyService.saveBatch(result.getList());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (flag){
            return R.ok();
        }else {
            return R.error("导入错误").put("batch",model);
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
        QueryWrapper<SysNotifyEntity> entityQueryWrapper = (new CustomWrapper(SysNotifyEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = sysNotifyService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<SysNotifyEntity> page = new Page<SysNotifyEntity>(i+1, EXPORT_MAX);
                    List<SysNotifyEntity> result = sysNotifyService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, SysNotifyEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<SysNotifyEntity> page = new Page<SysNotifyEntity>(1, EXPORT_MAX);
                List<SysNotifyEntity> result = sysNotifyService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,SysNotifyEntity.class, result);
                }
                String filename = "通知通告("+DateUtil.today()+")";
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