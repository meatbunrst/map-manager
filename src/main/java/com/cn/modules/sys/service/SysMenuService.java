package com.cn.modules.sys.service;

import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.Constant;
import com.cn.common.utils.MapUtils;
import com.cn.modules.sys.dao.SysMenuDao;
import com.cn.modules.sys.entity.SysMenuEntity;
import com.cn.modules.sys.vo.MenuDTO;
import com.cn.modules.sys.vo.MenuMetaVo;
import com.cn.modules.sys.vo.MenuVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
* 菜单表Service  业务接口
*
* @author zhangheng
* @date 2019-07-17 22:51:08
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
@CacheConfig(cacheNames = "menu")
public class SysMenuService extends AbstractService<SysMenuDao,SysMenuEntity>{

    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {

        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for(SysMenuEntity menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return dao.queryListParentId(parentId);
    }

    public List<SysMenuEntity> getUserMainMenu(Long userId) {
        List<SysMenuEntity> list = Lists.newArrayList();
        //用户菜单列表
        List<Long> menuIdList = systemUserService.queryAllMenuId(userId);
        list = queryListParentId(0L,menuIdList);
        return list;
    }

    public List<SysMenuEntity> queryNotButtonList(Long userId) {
        if(userId == Constant.SUPER_ADMIN){
            LambdaQueryWrapper<SysMenuEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.ne(SysMenuEntity::getType,2);
            wrapper.eq(SysMenuEntity::getStatus,'1');
            return this.list(wrapper);
        }else {
            return dao.queryNotButtonList(userId);
        }


    }

    @Transactional(rollbackFor={RuntimeException.class})
    public boolean updateById(SysMenuEntity entity) {
        return this.retBool(this.baseMapper.updateById(entity));
    }

    @Transactional(rollbackFor={RuntimeException.class})
    public boolean save(SysMenuEntity entity) {
        return this.retBool(this.baseMapper.insert(entity));
    }

    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //用户菜单列表
        List<Long> menuIdList = systemUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Transactional(rollbackFor={RuntimeException.class})
    public void delete(Long menuId){
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    public List<MenuDTO> buildTree(List<SysMenuEntity> menuList){
        List<MenuDTO> menuDTOS = Lists.newArrayList();
        for (SysMenuEntity entity : menuList) {
            MenuDTO vo = new MenuDTO();
            vo.setId(entity.getMenuId());
            vo.setComponent(entity.getUrl());

            vo.setIcon(entity.getIcon());
            vo.setPid(entity.getParentId());
            vo.setName(entity.getName());

            if (StrUtil.isBlank(entity.getUrl())){
                vo.setPath(entity.getMenuId().toString());
            }else {
                vo.setPath(entity.getUrl());
            }

            vo.setIFrame(false);
            vo.setSort(Long.valueOf(entity.getOrderNum()));
            menuDTOS.add(vo);
        }

        List<MenuDTO> trees = new ArrayList<>();
        for (MenuDTO menuDTO : menuDTOS) {

            if ("0".equals(menuDTO.getPid().toString())) {
                trees.add(menuDTO);
            }

            for (MenuDTO it : menuDTOS) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    public List<MenuVo> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();

        menuDTOS.forEach(menuDTO -> {
                    if (menuDTO!=null){
                        List<MenuDTO> menuDTOList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(menuDTO.getName());
                        menuVo.setPath(menuDTO.getPath());

                        // 如果不是外链
                        if(!menuDTO.getIFrame()){
                            if(menuDTO.getPid().equals(0L)){
                                //一级目录需要加斜杠，不然访问 会跳转404页面
                                menuVo.setPath("/" + menuDTO.getPath());
                                menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
                            }else if(!StrUtil.isEmpty(menuDTO.getComponent())){
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getName(),menuDTO.getIcon()));
                        if(menuDTOList!=null && menuDTOList.size()!=0){
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDTOList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if(menuDTO.getPid().equals(0L)){
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if(!menuDTO.getIFrame()){
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<MenuVo>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for(SysMenuEntity entity : menuList){
            //目录
            if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysMenuEntity model){
        return dao.selectCountByModel(model);
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysMenuEntity model){
        return SqlHelper.delBool(dao.deleteByModel(model));
    }

    /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param model 实体对象封装操作类（可以为 null）
    * @return List<SysMenuEntity>
    */
    public List<SysMenuEntity> selectList(SysMenuEntity model){
        return dao.selectListModel(model);
    }
    /**
    * <p>
    * 根据 entity 条件，查询全部记录（并翻页）
    * </p>
    *
    * @param pagination 分页查询条件
    * @param model   实体对象封装操作可以为 null）
    * @param wrapper   SQL包装
    * @return List<SysMenuEntity>
    */
    public List<SysMenuEntity> selectPage(Page pagination, SysMenuEntity model,Wrapper<SysMenuEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysMenuEntity>
    */
    public List<SysMenuEntity> selectPage(Page pagination,Wrapper<SysMenuEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }





}
