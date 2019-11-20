package com.cn.modules.sys.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.utils.ToolUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.modules.sys.entity.SysRoleMenuEntity;
import com.cn.modules.sys.dao.SysRoleMenuDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
* 角色与菜单对应关系Service  业务接口
*
* @author zhangheng
* @date 2019-09-06 15:20:09
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysRoleMenuService extends AbstractService<SysRoleMenuDao,SysRoleMenuEntity>{


    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
        for(Long menuId : menuIdList){
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            list.add(sysRoleMenuEntity);
        }
        this.saveBatch(list);
    }

    public List<Long> queryMenuIdList(Long roleId) {
        return dao.queryMenuIdList(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Long[] roleIds){
        return dao.deleteBatch(roleIds);
    }
    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysRoleMenuEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysRoleMenuEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysRoleMenuEntity> getWrapper(SysRoleMenuEntity model){
        LambdaQueryWrapper<SysRoleMenuEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getId())){
                    wrapper.eq(SysRoleMenuEntity::getId,model.getId());
                }
                if (ToolUtil.isNotEmpty(model.getRoleId())){
                    wrapper.eq(SysRoleMenuEntity::getRoleId,model.getRoleId());
                }
                if (ToolUtil.isNotEmpty(model.getMenuId())){
                    wrapper.eq(SysRoleMenuEntity::getMenuId,model.getMenuId());
                }
            }
        return wrapper;
    }
    /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param model 实体对象封装操作类（可以为 null）
    * @return List<SysRoleMenuEntity>
    */
    public List<SysRoleMenuEntity> selectList(SysRoleMenuEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysRoleMenuEntity> selectPage(Page pagination,SysRoleMenuEntity model){
        return dao.queryPage(pagination,getWrapper(model));
    }
    /**
    * <p>
    * 根据 entity 条件，查询全部记录（并翻页）
    * </p>
    *
    * @param pagination 分页查询条件
    * @param model   实体对象封装操作可以为 null）
    * @param wrapper   SQL包装
    * @return List<SysRoleMenuEntity>
    */
    public List<SysRoleMenuEntity> selectPage(Page pagination, SysRoleMenuEntity model,QueryWrapper<SysRoleMenuEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysRoleMenuEntity>
    */
    public List<SysRoleMenuEntity> selectPage(Page pagination,QueryWrapper<SysRoleMenuEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
