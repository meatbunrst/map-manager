package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.dao.SysRoleDao;
import com.cn.modules.sys.entity.SysRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 角色Service  业务接口
*
* @author zhangheng
* @date 2019-08-30 17:28:20
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysRoleService extends AbstractService<SysRoleDao,SysRoleEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysRoleEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysRoleEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysRoleEntity> getWrapper(SysRoleEntity model){
        LambdaQueryWrapper<SysRoleEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getRoleId())){
                    wrapper.eq(SysRoleEntity::getRoleId,model.getRoleId());
                }
                if (ToolUtil.isNotEmpty(model.getRoleName())){
                    wrapper.eq(SysRoleEntity::getRoleName,model.getRoleName());
                }
                if (ToolUtil.isNotEmpty(model.getRemark())){
                    wrapper.eq(SysRoleEntity::getRemark,model.getRemark());
                }
                if (ToolUtil.isNotEmpty(model.getCreateUserId())){
                    wrapper.eq(SysRoleEntity::getCreateUserId,model.getCreateUserId());
                }
                if (ToolUtil.isNotEmpty(model.getCreateTime())){
                    wrapper.eq(SysRoleEntity::getCreateTime,model.getCreateTime());
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
    * @return List<SysRoleEntity>
    */
    public List<SysRoleEntity> selectList(SysRoleEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysRoleEntity> selectPage(Page pagination,SysRoleEntity model){
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
    * @return List<SysRoleEntity>
    */
    public List<SysRoleEntity> selectPage(Page pagination, SysRoleEntity model,QueryWrapper<SysRoleEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysRoleEntity>
    */
    public List<SysRoleEntity> selectPage(Page pagination,QueryWrapper<SysRoleEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
