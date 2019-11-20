package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.dao.SysDictDetailedDao;
import com.cn.modules.sys.entity.SysDictDetailedEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 系统配置信息表Service  业务接口
*
* @author zhangheng
* @date 2019-08-30 17:40:18
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysDictDetailedService extends AbstractService<SysDictDetailedDao,SysDictDetailedEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysDictDetailedEntity model){
        return count(countWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysDictDetailedEntity model){
        return remove(getWrapper(model));
    }

    /**
     * 根据 model 条件，生成LambdaQueryWrapper
     *
     * @param model 实体对象
     * @return LambdaQueryWrapper
     */
    public LambdaQueryWrapper<SysDictDetailedEntity> countWrapper(SysDictDetailedEntity model){
        LambdaQueryWrapper<SysDictDetailedEntity> wrapper = new LambdaQueryWrapper<>();
        if (model != null){
            if (ToolUtil.isNotEmpty(model.getId())){
                wrapper.ne(SysDictDetailedEntity::getId,model.getId());
            }
            if (ToolUtil.isNotEmpty(model.getParamKey())){
                wrapper.eq(SysDictDetailedEntity::getParamKey,model.getParamKey());
            }
            if (ToolUtil.isNotEmpty(model.getParamValue())){
                wrapper.eq(SysDictDetailedEntity::getParamValue,model.getParamValue());
            }

            if (ToolUtil.isNotEmpty(model.getLabel())){
                wrapper.eq(SysDictDetailedEntity::getLabel,model.getLabel());
            }
            if (ToolUtil.isNotEmpty(model.getLabelId())){
                wrapper.eq(SysDictDetailedEntity::getLabelId,model.getLabelId());
            }
        }
        return wrapper;
    }
    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysDictDetailedEntity> getWrapper(SysDictDetailedEntity model){
        LambdaQueryWrapper<SysDictDetailedEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getId())){
                    wrapper.eq(SysDictDetailedEntity::getId,model.getId());
                }
                if (ToolUtil.isNotEmpty(model.getParamKey())){
                    wrapper.eq(SysDictDetailedEntity::getParamKey,model.getParamKey());
                }
                if (ToolUtil.isNotEmpty(model.getParamValue())){
                    wrapper.eq(SysDictDetailedEntity::getParamValue,model.getParamValue());
                }
                if (ToolUtil.isNotEmpty(model.getStatus())){
                    wrapper.eq(SysDictDetailedEntity::getStatus,model.getStatus());
                }
                if (ToolUtil.isNotEmpty(model.getRemark())){
                    wrapper.eq(SysDictDetailedEntity::getRemark,model.getRemark());
                }
                if (ToolUtil.isNotEmpty(model.getLabel())){
                    wrapper.eq(SysDictDetailedEntity::getLabel,model.getLabel());
                }
                if (ToolUtil.isNotEmpty(model.getLabelId())){
                    wrapper.eq(SysDictDetailedEntity::getLabelId,model.getLabelId());
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
    * @return List<SysDictDetailedEntity>
    */
    public List<SysDictDetailedEntity> selectList(SysDictDetailedEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysDictDetailedEntity> selectPage(Page pagination,SysDictDetailedEntity model){
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
    * @return List<SysDictDetailedEntity>
    */
    public List<SysDictDetailedEntity> selectPage(Page pagination, SysDictDetailedEntity model,QueryWrapper<SysDictDetailedEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysDictDetailedEntity>
    */
    public List<SysDictDetailedEntity> selectPage(Page pagination,QueryWrapper<SysDictDetailedEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
