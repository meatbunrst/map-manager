package com.cn.modules.sys.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.utils.ToolUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.modules.sys.entity.SysMenuGroupEntity;
import com.cn.modules.sys.dao.SysMenuGroupDao;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
* 代码生成组Service  业务接口
*
* @author zhangheng
* @date 2019-09-18 21:17:37
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysMenuGroupService extends AbstractService<SysMenuGroupDao,SysMenuGroupEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysMenuGroupEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysMenuGroupEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysMenuGroupEntity> getWrapper(SysMenuGroupEntity model){
        LambdaQueryWrapper<SysMenuGroupEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getId())){
                    wrapper.eq(SysMenuGroupEntity::getId,model.getId());
                }
                if (ToolUtil.isNotEmpty(model.getName())){
                    wrapper.eq(SysMenuGroupEntity::getName,model.getName());
                }
                if (ToolUtil.isNotEmpty(model.getCode())){
                    wrapper.eq(SysMenuGroupEntity::getCode,model.getCode());
                }
                if (ToolUtil.isNotEmpty(model.getCreateTime())){
                    wrapper.eq(SysMenuGroupEntity::getCreateTime,model.getCreateTime());
                }
                if (ToolUtil.isNotEmpty(model.getUpdateTime())){
                    wrapper.eq(SysMenuGroupEntity::getUpdateTime,model.getUpdateTime());
                }
                if (ToolUtil.isNotEmpty(model.getRemarks())){
                    wrapper.eq(SysMenuGroupEntity::getRemarks,model.getRemarks());
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
    * @return List<SysMenuGroupEntity>
    */
    public List<SysMenuGroupEntity> selectList(SysMenuGroupEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysMenuGroupEntity> selectPage(Page pagination,SysMenuGroupEntity model){
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
    * @return List<SysMenuGroupEntity>
    */
    public List<SysMenuGroupEntity> selectPage(Page pagination, SysMenuGroupEntity model,QueryWrapper<SysMenuGroupEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysMenuGroupEntity>
    */
    public List<SysMenuGroupEntity> selectPage(Page pagination,QueryWrapper<SysMenuGroupEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
