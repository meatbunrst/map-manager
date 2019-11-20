package com.cn.modules.sys.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.utils.ToolUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.modules.sys.entity.SysMenuItemEntity;
import com.cn.modules.sys.dao.SysMenuItemDao;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
* 菜单条件Service  业务接口
*
* @author zhangheng
* @date 2019-09-18 21:17:28
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysMenuItemService extends AbstractService<SysMenuItemDao,SysMenuItemEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysMenuItemEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysMenuItemEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysMenuItemEntity> getWrapper(SysMenuItemEntity model){
        LambdaQueryWrapper<SysMenuItemEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getId())){
                    wrapper.eq(SysMenuItemEntity::getId,model.getId());
                }
                if (ToolUtil.isNotEmpty(model.getName())){
                    wrapper.eq(SysMenuItemEntity::getName,model.getName());
                }
                if (ToolUtil.isNotEmpty(model.getMenuSort())){
                    wrapper.eq(SysMenuItemEntity::getMenuSort,model.getMenuSort());
                }
                if (ToolUtil.isNotEmpty(model.getSysId())){
                    wrapper.eq(SysMenuItemEntity::getSysId,model.getSysId());
                }
                if (ToolUtil.isNotEmpty(model.getPermission())){
                    wrapper.eq(SysMenuItemEntity::getPermission,model.getPermission());
                }
                if (ToolUtil.isNotEmpty(model.getMenuType())){
                    wrapper.eq(SysMenuItemEntity::getMenuType,model.getMenuType());
                }
                if (ToolUtil.isNotEmpty(model.getMeenuHref())){
                    wrapper.eq(SysMenuItemEntity::getMeenuHref,model.getMeenuHref());
                }
                if (ToolUtil.isNotEmpty(model.getRemarks())){
                    wrapper.eq(SysMenuItemEntity::getRemarks,model.getRemarks());
                }
                if (ToolUtil.isNotEmpty(model.getCreateTime())){
                    wrapper.eq(SysMenuItemEntity::getCreateTime,model.getCreateTime());
                }
                if (ToolUtil.isNotEmpty(model.getUpdateTime())){
                    wrapper.eq(SysMenuItemEntity::getUpdateTime,model.getUpdateTime());
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
    * @return List<SysMenuItemEntity>
    */
    public List<SysMenuItemEntity> selectList(SysMenuItemEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysMenuItemEntity> selectPage(Page pagination,SysMenuItemEntity model){
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
    * @return List<SysMenuItemEntity>
    */
    public List<SysMenuItemEntity> selectPage(Page pagination, SysMenuItemEntity model,QueryWrapper<SysMenuItemEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysMenuItemEntity>
    */
    public List<SysMenuItemEntity> selectPage(Page pagination,QueryWrapper<SysMenuItemEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
