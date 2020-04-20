package com.cn.modules.sys.controller;

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
import com.cn.common.utils.Result;
import com.cn.modules.sys.entity.SysDictDetailedEntity;
import com.cn.modules.sys.entity.SysDictEntity;
import com.cn.modules.sys.service.SysDictDetailedService;
import com.cn.modules.sys.service.SysDictService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
* 系统配置信息表控制器
*
* @author zhangheng
* @date 2019-08-30 17:40:18
*/
@RestController
@RequestMapping("/sysdictdetailed")
@Slf4j
public class SysDictDetailedController extends AbstractController {

    @Autowired
    private SysDictDetailedService sysDictDetailedService;

    @Autowired
    private SysDictService sysDictService;
    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<SysDictDetailedEntity> page = new PageFactory<SysDictDetailedEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysDictDetailedEntity> entityQueryWrapper = (new CustomWrapper(SysDictDetailedEntity.class)).parseSqlWhere(list);
        page.setRecords( sysDictDetailedService.selectPage(page,entityQueryWrapper));

        return Result.ok().put("page", new PageUtils(page));
    }

    /**
    * 获取分页信息
    * @param entity 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/listModel")
    public Object list(SysDictDetailedEntity entity) {
    Page<SysDictDetailedEntity> page = new PageFactory<SysDictDetailedEntity>().defaultPage();
        page.setRecords( sysDictDetailedService.selectPage(page,entity));
        return Result.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("sysdictdetailed:add")
    public Object add(@RequestBody SysDictDetailedEntity entity) {
        sysDictDetailedService.save(entity);
        return Result.ok();
    }
    @GetMapping(value = "/dictDetail")
    public Object dictDetail(String name) {

        QueryWrapper<SysDictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_name",name);
        SysDictEntity entity = (SysDictEntity) sysDictService.getOne(queryWrapper);

        SysDictDetailedEntity dictDetailedEntity = new SysDictDetailedEntity();
        dictDetailedEntity.setLabelId(entity.getId());
        List<SysDictDetailedEntity> list = sysDictDetailedService.selectList(dictDetailedEntity);

        return Result.ok().put("list",list);
    }

    @GetMapping(value = "/dictDetail/map")
    public Object getDictDetailMaps(String name) {

        Map map = Maps.newHashMap();
        Arrays.asList(name.split("\\s*,\\s*")).forEach(s -> {

            QueryWrapper<SysDictEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dict_name",s);
            SysDictEntity entity = (SysDictEntity) sysDictService.getOne(queryWrapper);

            if (entity != null){
                SysDictDetailedEntity dictDetailedEntity = new SysDictDetailedEntity();
                dictDetailedEntity.setLabelId(entity.getId());
                List<SysDictDetailedEntity> list = sysDictDetailedService.selectList(dictDetailedEntity);
                map.put(s,list);
            }
        });
        return Result.ok().put("map",map);
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("sysdictdetailed:delete")
    public Object delete(@RequestBody Integer[] ids) {
        sysDictDetailedService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 测试
     * @param entity 对象
     * @return Object 执行结果
     */
    @GetMapping(value = "/count")
    public Object count( SysDictDetailedEntity entity) {
        entity.setRemark(null);
        entity.setStatus(null);

        boolean flag = sysDictDetailedService.selectCount(entity) > 0;
        return Result.ok().put("flag",flag);
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SysDictDetailedEntity entity) {
        sysDictDetailedService.updateById(entity);
        return Result.ok();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(SysDictDetailedEntity entity) {
        return Result.ok().put("sysdictdetailed", sysDictDetailedService.selectOne(entity));
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
        QueryWrapper<SysDictDetailedEntity> entityQueryWrapper = (new CustomWrapper(SysDictDetailedEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = sysDictDetailedService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<SysDictDetailedEntity> page = new Page<SysDictDetailedEntity>(i+1, EXPORT_MAX);
                    List<SysDictDetailedEntity> result = sysDictDetailedService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, SysDictDetailedEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<SysDictDetailedEntity> page = new Page<SysDictDetailedEntity>(1, EXPORT_MAX);
                List<SysDictDetailedEntity> result = sysDictDetailedService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams,SysDictDetailedEntity.class, result);
                }
                String filename = "系统配置信息表("+DateUtil.today()+")";
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