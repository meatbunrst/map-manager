package com.cn.modules.debug.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.annotation.SysLog;
import com.cn.common.constant.Constant;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.R;
import com.cn.modules.debug.entity.DebugModuleEntity;
import com.cn.modules.debug.service.DebugModuleService;
import com.cn.modules.debug.vo.DebugModuleVo;
import com.cn.modules.sys.controller.AbstractController;
import com.cn.modules.sys.entity.SysMenuEntity;
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
* 模板表控制器
*
* @author zhangheng
* @date 2019-05-25 23:23:02
*/
@RestController
@RequestMapping("/debugmodule")
@Slf4j
public class DebugModuleController extends AbstractController {

    @Autowired
    private DebugModuleService debugModuleService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<DebugModuleEntity> page = new PageFactory<DebugModuleEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<DebugModuleEntity> entityQueryWrapper = (new CustomWrapper(DebugModuleEntity.class)).parseSqlWhere(list);
        page.setRecords(debugModuleService.selectPage(page,entityQueryWrapper));
        return R.ok().put("page", new PageUtils(page));
    }


    @GetMapping(value = "/all")
    public Object all(DebugModuleEntity entity) {

        List<DebugModuleEntity> list =debugModuleService.selectList(entity);
        return R.ok().put("all",list);
    }


    @SysLog("删除菜单")
    @PostMapping("/deleteAll/{id}")
    public R deleteAll(@PathVariable("id") long id){

        QueryWrapper<DebugModuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        List<DebugModuleEntity> list = debugModuleService.list(queryWrapper);

        for (DebugModuleEntity moduleEntity : list) {
            debugModuleService.removeById(moduleEntity.getId());
        }
        debugModuleService.removeById(id);
        return R.ok();
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    public Object add(@RequestBody DebugModuleEntity entity) {
        debugModuleService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("debugmodule:delete")
    public Object delete(@RequestBody String[] ids) {
        debugModuleService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody DebugModuleEntity entity) {
        debugModuleService.updateById(entity);
        return R.ok().put("module",entity);
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(DebugModuleEntity entity) {
        return R.ok().put("debugmodule", debugModuleService.selectOne(entity));
    }



    /**
     * 修改
     */
    @PostMapping("/updateNode")
    public Object updateNode(@RequestBody DebugModuleVo nodeVO){
        UpdateWrapper<DebugModuleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",nodeVO.getDraggingNodeId());
        DebugModuleEntity debugModuleEntity = (DebugModuleEntity) debugModuleService.getById(nodeVO.getDraggingNodeId());

        if (nodeVO.getDraggingNodeId() != null){
            if( nodeVO.getDropType().equals(Constant.DropStatus.INNER.getValue()) ){
                updateWrapper.set("pid",nodeVO.getDropNodeId());
                debugModuleService.update(updateWrapper);

            } else if (nodeVO.getDropType().equals(Constant.DropStatus.BEFORE.getValue())){
                DebugModuleEntity entity = (DebugModuleEntity)debugModuleService.getById(nodeVO.getDropNodeId());
                updateWrapper.set("pid",entity.getPid());

                updateWrapper.set("NUM",entity.getNum()+1);
                debugModuleService.update(updateWrapper);
            } else {

                DebugModuleEntity entity = (DebugModuleEntity)debugModuleService.getById(nodeVO.getDropNodeId());
                QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pid",entity.getPid());

                queryWrapper.ge("NUM",entity.getNum() );
                List<DebugModuleEntity> entities = debugModuleService.list(queryWrapper);

                entities.forEach(moduleEntity -> {
                    moduleEntity.setNum(moduleEntity.getNum()+1);
                });

                updateWrapper.set("pid",entity.getPid());

                updateWrapper.set("NUM",entity.getNum());
                debugModuleService.update(updateWrapper);
                debugModuleService.updateBatchById(entities);
            }
//            最后结束所有的
            QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid",debugModuleEntity.getPid());

        }

        return R.ok();
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
        QueryWrapper<DebugModuleEntity> entityQueryWrapper = (new CustomWrapper(DebugModuleEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = debugModuleService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<DebugModuleEntity> page = new Page<DebugModuleEntity>(i+1, EXPORT_MAX);
                    List<DebugModuleEntity> result = debugModuleService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, DebugModuleEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<DebugModuleEntity> page = new Page<DebugModuleEntity>(1, EXPORT_MAX);
                List<DebugModuleEntity> result = debugModuleService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,DebugModuleEntity.class, result);
                }
                String filename = "模板表("+DateUtil.today()+")";
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