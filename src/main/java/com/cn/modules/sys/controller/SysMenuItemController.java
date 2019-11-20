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
import com.cn.modules.sys.entity.SysMenuItemEntity;
import com.cn.modules.sys.service.SysMenuItemService;
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
* 菜单条件控制器
*
* @author zhangheng
* @date 2019-09-18 21:17:28
*/
@RestController
@RequestMapping("/sysmenuitem")
@Slf4j
public class SysMenuItemController extends AbstractController {

    @Autowired
    private SysMenuItemService sysMenuItemService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<SysMenuItemEntity> page = new PageFactory<SysMenuItemEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysMenuItemEntity> entityQueryWrapper = (new CustomWrapper(SysMenuItemEntity.class)).parseSqlWhere(list);
        page.setRecords( sysMenuItemService.selectPage(page,entityQueryWrapper));

        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 获取分页信息
    * @param entity 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/listModel")
    public Object list(SysMenuItemEntity entity) {
    Page<SysMenuItemEntity> page = new PageFactory<SysMenuItemEntity>().defaultPage();
        page.setRecords( sysMenuItemService.selectPage(page,entity));
        return R.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("sysmenuitem:add")
    public Object add(@RequestBody SysMenuItemEntity entity) {
        sysMenuItemService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("sysmenuitem:delete")
    public Object delete(@RequestBody String[] ids) {
        sysMenuItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SysMenuItemEntity entity) {
        sysMenuItemService.updateById(entity);
        return R.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(SysMenuItemEntity entity) {
        return R.ok().put("sysmenuitem", sysMenuItemService.selectOne(entity));
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
            ExcelImportResult<SysMenuItemEntity> result = ExcelImportUtil.importExcelMore(
                    inputStream,SysMenuItemEntity.class, params);
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
                sysMenuItemService.saveBatch(result.getList());
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
        QueryWrapper<SysMenuItemEntity> entityQueryWrapper = (new CustomWrapper(SysMenuItemEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = sysMenuItemService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<SysMenuItemEntity> page = new Page<SysMenuItemEntity>(i+1, EXPORT_MAX);
                    List<SysMenuItemEntity> result = sysMenuItemService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, SysMenuItemEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<SysMenuItemEntity> page = new Page<SysMenuItemEntity>(1, EXPORT_MAX);
                List<SysMenuItemEntity> result = sysMenuItemService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,SysMenuItemEntity.class, result);
                }
                String filename = "菜单条件("+DateUtil.today()+")";
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