package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.MapUtils;
import com.cn.modules.sys.dao.SysUserRoleDao;
import com.cn.modules.sys.entity.SysUserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* 角色用户Service  业务接口
*
* @author zhangheng
* @date 2019-05-14 17:37:20
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysUserRoleService extends AbstractService<SysUserRoleDao, SysUserRoleEntity> {

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysUserRoleEntity model){
        return dao.selectCountByModel(model);
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysUserRoleEntity model){
        return SqlHelper.delBool(dao.deleteByModel(model));
    }

    /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param model 实体对象封装操作类（可以为 null）
    * @return List<SysUserRoleEntity>
    */
    public List<SysUserRoleEntity> selectList(SysUserRoleEntity model){
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
    * @return List<SysUserRoleEntity>
    */
    public List<SysUserRoleEntity> selectPage(Page pagination, SysUserRoleEntity model,Wrapper<SysUserRoleEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysUserRoleEntity>
    */
    public List<SysUserRoleEntity> selectPage(Page pagination,Wrapper<SysUserRoleEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.removeByMap(new MapUtils().put("user_id", userId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
        for(Long roleId : roleIdList){
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            list.add(sysUserRoleEntity);
        }
        this.saveBatch(list);
    }

    public List<Long> queryRoleIdList(Long userId) {
        return dao.queryRoleIdList(userId);
    }

    public int deleteBatch(Long[] roleIds){
        return dao.deleteBatch(roleIds);
    }
}
