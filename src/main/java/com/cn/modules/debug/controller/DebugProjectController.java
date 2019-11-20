package com.cn.modules.debug.controller;

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
import com.cn.modules.debug.entity.DebugModuleEntity;
import com.cn.modules.debug.entity.DebugProjectEntity;
import com.cn.modules.debug.service.DebugModuleService;
import com.cn.modules.debug.service.DebugProjectService;
import com.cn.modules.sys.controller.AbstractController;
import com.cn.modules.sys.entity.BatchAttachEntity;
import com.cn.modules.sys.utils.AttachUtils;
import com.cn.modules.sys.vo.TreeVo;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
/**
* 项目表控制器
*
* @author zhangheng
* @date 2019-05-25 23:11:09
*/
@RestController
@RequestMapping("/debugproject")
@Slf4j
public class DebugProjectController extends AbstractController {



    @Autowired
    private DebugProjectService debugProjectService;

    @Autowired
    private DebugModuleService debugModuleService;
    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<DebugProjectEntity> page = new PageFactory<DebugProjectEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<DebugProjectEntity> entityQueryWrapper = (new CustomWrapper(DebugProjectEntity.class)).parseSqlWhere(list);
        page.setRecords( debugProjectService.selectPage(page,entityQueryWrapper));
        return R.ok().put("page", new PageUtils(page));
    }

    @GetMapping(value = "/all")
    public Object all() {

        DebugProjectEntity entity = new DebugProjectEntity();
        List<DebugProjectEntity> list =debugProjectService.selectList(entity);
        return R.ok().put("all",list);
    }


    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("debugproject:add")
    public Object add(@RequestBody DebugProjectEntity entity) {


        debugProjectService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("debugproject:delete")
    public Object delete(@RequestBody Integer[] ids) {
        debugProjectService.removeByIds(Arrays.asList(ids));
        for (Integer id : ids) {
            QueryWrapper<DebugModuleEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("project_id",id);

            debugModuleService.remove(queryWrapper);
        }
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody DebugProjectEntity entity) {
        debugProjectService.updateById(entity);
        return R.ok();
    }


    /**
     * 详情
     * @param entity 对象
     * @return Object 详细对象
     */
    @GetMapping(value = "/tree")
    public Object tree(DebugProjectEntity entity) {
        entity = debugProjectService.selectOne(entity);

        QueryWrapper<DebugModuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id",entity.getId());
        queryWrapper.orderByDesc("num");

        List<DebugModuleEntity> list = debugModuleService.list(queryWrapper);

        List<TreeVo> jsTreeChildren = new ArrayList<>();
        TreeVo top = new TreeVo(String.valueOf(entity.getId()),entity.getName(),"",false);
        for (DebugModuleEntity moduleEntity : list) {
            if (moduleEntity.getId() != null ){
                TreeVo node = new TreeVo(String.valueOf(moduleEntity.getId()),moduleEntity.getFullname(),"",false);
                node.setChildren(this.getResChrild(list,moduleEntity));
                jsTreeChildren.add(node);
            }
            top.setChildren(jsTreeChildren);
        }
        return top;
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(DebugProjectEntity entity) {
        return R.ok().put("debugproject", debugProjectService.selectOne(entity));
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
            ExcelImportResult<DebugProjectEntity> result = ExcelImportUtil.importExcelMore(
                    inputStream,DebugProjectEntity.class, params);
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
                debugProjectService.saveBatch(result.getList());
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
        QueryWrapper<DebugProjectEntity> entityQueryWrapper = (new CustomWrapper(DebugProjectEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = debugProjectService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<DebugProjectEntity> page = new Page<DebugProjectEntity>(i+1, EXPORT_MAX);
                    List<DebugProjectEntity> result = debugProjectService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, DebugProjectEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<DebugProjectEntity> page = new Page<DebugProjectEntity>(1, EXPORT_MAX);
                List<DebugProjectEntity> result = debugProjectService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,DebugProjectEntity.class, result);
                }
                String filename = "项目表("+DateUtil.today()+")";
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

    private List<TreeVo> getResChrild(List<DebugModuleEntity> list, DebugModuleEntity sysRes){
        List<TreeVo> treeVos = new ArrayList<>();
        for (DebugModuleEntity res:list){
            if (res.getPid() != null && sysRes.getId().equals(res.getPid())  ){

                TreeVo node = new TreeVo(String.valueOf(res.getId()),res.getFullname(),"",false);
                node.setChildren(getResChrild(list,res));
                treeVos.add(node);
            }
        }
        return treeVos;
    }
}