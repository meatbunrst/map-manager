package com.cn.modules.sys.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.annotation.SysLog;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.Result;
import com.cn.common.validator.ValidatorUtils;
import com.cn.modules.sys.entity.SysRoleEntity;
import com.cn.modules.sys.entity.SysUserRoleEntity;
import com.cn.modules.sys.service.SysRoleMenuService;
import com.cn.modules.sys.service.SysRoleService;
import com.cn.modules.sys.service.SysUserRoleService;
import com.cn.modules.sys.vo.SysRoleVo;
import com.cn.modules.sys.vo.TreeVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
/**
* 角色控制器
*
* @author zhangheng
* @date 2019-08-30 17:28:20
*/
@RestController
@RequestMapping("/sysrole")
@Slf4j
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<SysRoleEntity> page = new PageFactory<SysRoleEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SysRoleEntity> entityQueryWrapper = (new CustomWrapper(SysRoleEntity.class)).parseSqlWhere(list);
        page.setRecords( sysRoleService.selectPage(page,entityQueryWrapper));

        return Result.ok().put("page", new PageUtils(page));
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    public Result select(){
        Map<String, Object> map = new HashMap<>();

        List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
        return Result.ok().put("list", list);
    }
    /**
     * 角色列表
     */
    @GetMapping("/all")
    public Result all(){
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
        List<TreeVo> treeVos = Lists.newArrayList();

        list.forEach(sysRoleEntity -> {
            TreeVo treeVo = new TreeVo(String.valueOf(sysRoleEntity.getRoleId()),sysRoleEntity.getRoleName(),null,false);
            treeVos.add(treeVo);
        });
        return Result.ok().put("list", treeVos);
    }
    /**
    * 获取分页信息
    * @param entity 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/listModel")
    public Object list(SysRoleEntity entity) {
    Page<SysRoleEntity> page = new PageFactory<SysRoleEntity>().defaultPage();
        page.setRecords( sysRoleService.selectPage(page,entity));
        return Result.ok().put("page", new PageUtils(page));
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("sysrole:add")
    public Object add(@RequestBody SysRoleEntity entity) {
        sysRoleService.save(entity);
        return Result.ok();
    }

    /**
     * 角色列表
     */
    @GetMapping("/dragData")
    public Object dropData(Long userId){

        List<SysRoleEntity> list = sysRoleService.list();
        List<Long> longList = sysUserRoleService.queryRoleIdList(userId);

        List<SysRoleEntity> result = list.stream().filter(line -> longList.contains(line.getRoleId()))
                .collect(Collectors.toList());
        list.removeAll(result);
        return Result.ok().put("undo", list).put("done", result);
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    public Object delete(@RequestBody Long[] ids) {
        sysRoleService.removeByIds(Arrays.asList(ids));

        for (Long id : ids) {
            QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ROLE_ID",id);
            sysUserRoleService.remove(queryWrapper);
        }
        sysRoleMenuService.deleteBatch(ids);
        return Result.ok();
    }


    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @PostMapping("/save")
    public Result save(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        role.setCreateTime(new Date());
        sysRoleService.save(role);

        sysRoleMenuService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());

        return Result.ok();
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    public Result info(@PathVariable("roleId") Long roleId){
        SysRoleEntity role = (SysRoleEntity) sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return Result.ok().put("role", role);
    }

    /**
     * 分配角色
     */
    @SysLog("分配角色")
    @PostMapping("/assignment")
    public Result assignment(@RequestBody SysRoleVo role){

        List<SysUserRoleEntity> roleEntities = Lists.newArrayList();
        List<Long> roleIds = Lists.newArrayList();

        if (role.getRoleEntities().size() > 0){
            QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("USER_ID",role.getUserId());
            sysUserRoleService.remove(queryWrapper);

            role.getRoleEntities().forEach(sysRoleEntity -> {

                SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
                userRoleEntity.setUserId(role.getUserId());
                userRoleEntity.setRoleId(sysRoleEntity.getRoleId());
                roleEntities.add(userRoleEntity);
            });
        }

        sysUserRoleService.saveBatch(roleEntities);
        return Result.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @PostMapping("/update")
    public Result update(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.updateById(role);
        sysRoleMenuService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());
        return Result.ok();
    }

}