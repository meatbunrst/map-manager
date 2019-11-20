package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.dao.SysTaskDao;
import com.cn.modules.sys.entity.SysTaskEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 任务通知表Service  业务接口
*
* @author zhangheng
* @date 2019-08-09 15:29:50
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysTaskService extends AbstractService<SysTaskDao,SysTaskEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysTaskEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysTaskEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysTaskEntity> getWrapper(SysTaskEntity model){
        LambdaQueryWrapper<SysTaskEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getId())){
                    wrapper.eq(SysTaskEntity::getId,model.getId());
                }
                if (ToolUtil.isNotEmpty(model.getTitle())){
                    wrapper.eq(SysTaskEntity::getTitle,model.getTitle());
                }
                if (ToolUtil.isNotEmpty(model.getContent())){
                    wrapper.eq(SysTaskEntity::getContent,model.getContent());
                }
                if (ToolUtil.isNotEmpty(model.getUserId())){
                    wrapper.eq(SysTaskEntity::getUserId,model.getUserId());
                }
                if (ToolUtil.isNotEmpty(model.getType())){
                    wrapper.eq(SysTaskEntity::getType,model.getType());
                }
                if (ToolUtil.isNotEmpty(model.getStatus())){
                    wrapper.eq(SysTaskEntity::getStatus,model.getStatus());
                }
                if (ToolUtil.isNotEmpty(model.getProjectId())){
                    wrapper.eq(SysTaskEntity::getProjectId,model.getProjectId());
                }
                if (ToolUtil.isNotEmpty(model.getCreateDate())){
                    wrapper.eq(SysTaskEntity::getCreateDate,model.getCreateDate());
                }
                if (ToolUtil.isNotEmpty(model.getCreateBy())){
                    wrapper.eq(SysTaskEntity::getCreateBy,model.getCreateBy());
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
    * @return List<SysTaskEntity>
    */
    public List<SysTaskEntity> selectList(SysTaskEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysTaskEntity> selectPage(Page pagination,SysTaskEntity model){
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
    * @return List<SysTaskEntity>
    */
    public List<SysTaskEntity> selectPage(Page pagination, SysTaskEntity model,QueryWrapper<SysTaskEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysTaskEntity>
    */
    public List<SysTaskEntity> selectPage(Page pagination,QueryWrapper<SysTaskEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
