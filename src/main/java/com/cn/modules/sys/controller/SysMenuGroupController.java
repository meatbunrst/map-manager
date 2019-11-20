package com.cn.modules.sys.controller;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.controller.AbstractController;
import org.springframework.web.bind.annotation.*;
import com.cn.modules.sys.entity.SysMenuGroupEntity;
import com.cn.modules.sys.service.SysMenuGroupService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import com.cn.common.factory.PageFactory;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.R;
import com.cn.modules.sys.entity.BatchAttachEntity;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import java.util.Map;
import com.cn.modules.sys.utils.AttachUtils;
import java.io.InputStream;
import java.util.Date;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
/**
* 代码生成组控制器
*
* @author zhangheng
* @date 2019-09-18 21:17:38
*/
@RestController
@RequestMapping("/sysmenugroup")
@Slf4j
public class SysMenuGroupController extends AbstractController {

    @Autowired
    private SysMenuGroupService sysMenuGroupService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<SysMenuGroupEntity> page = new PageFactory<SysMenuGroupEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysMenuGroupEntity> entityQueryWrapper = (new CustomWrapper(SysMenuGroupEntity.class)).parseSqlWhere(list);
        page.setRecords( sysMenuGroupService.selectPage(page,entityQueryWrapper));

        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 获取分页信息
    * @param entity 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/listModel")
    public Object list(SysMenuGroupEntity entity) {
    Page<SysMenuGroupEntity> page = new PageFactory<SysMenuGroupEntity>().defaultPage();
        page.setRecords( sysMenuGroupService.selectPage(page,entity));
        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("sysmenugroup:add")
    public Object add(@RequestBody SysMenuGroupEntity entity) {
        sysMenuGroupService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("sysmenugroup:delete")
    public Object delete(@RequestBody String[] ids) {
        sysMenuGroupService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SysMenuGroupEntity entity) {
        sysMenuGroupService.updateById(entity);
        return R.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(SysMenuGroupEntity entity) {
        return R.ok().put("sysmenugroup", sysMenuGroupService.selectOne(entity));
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
            ExcelImportResult<SysMenuGroupEntity> result = ExcelImportUtil.importExcelMore(
                    inputStream,SysMenuGroupEntity.class, params);
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
                sysMenuGroupService.saveBatch(result.getList());
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
        QueryWrapper<SysMenuGroupEntity> entityQueryWrapper = (new CustomWrapper(SysMenuGroupEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = sysMenuGroupService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<SysMenuGroupEntity> page = new Page<SysMenuGroupEntity>(i+1, EXPORT_MAX);
                    List<SysMenuGroupEntity> result = sysMenuGroupService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, SysMenuGroupEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<SysMenuGroupEntity> page = new Page<SysMenuGroupEntity>(1, EXPORT_MAX);
                List<SysMenuGroupEntity> result = sysMenuGroupService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,SysMenuGroupEntity.class, result);
                }
                String filename = "代码生成组("+DateUtil.today()+")";
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