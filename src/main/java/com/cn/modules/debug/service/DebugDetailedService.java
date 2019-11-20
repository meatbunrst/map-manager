package com.cn.modules.debug.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.debug.dao.DebugDetailedDao;
import com.cn.modules.debug.entity.DebugDetailedEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* bug详情Service  业务接口
*
* @author zhangheng
* @date 2019-05-25 23:36:55
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class DebugDetailedService extends AbstractService<DebugDetailedDao, DebugDetailedEntity>{

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    public Integer selectCount(DebugDetailedEntity model){
        return count(getWrapper(model));
    }

    /**
     * 根据 model 条件，删除
     *
     * @param model 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(DebugDetailedEntity model){
        return remove(getWrapper(model));
    }
    /**
     * 根据 model 条件，生成LambdaQueryWrapper
     *
     * @param model 实体对象
     * @return LambdaQueryWrapper
     */
    public LambdaQueryWrapper<DebugDetailedEntity> getWrapper(DebugDetailedEntity model){
        LambdaQueryWrapper<DebugDetailedEntity> wrapper = new LambdaQueryWrapper<>();
        if (model != null){
            if (ToolUtil.isNotEmpty(model.getId())){
                wrapper.eq(DebugDetailedEntity::getId,model.getId());
            }
            if (ToolUtil.isNotEmpty(model.getTitle())){
                wrapper.eq(DebugDetailedEntity::getTitle,model.getTitle());
            }
            if (ToolUtil.isNotEmpty(model.getModuleId())){
                wrapper.eq(DebugDetailedEntity::getModuleId,model.getModuleId());
            }
            if (ToolUtil.isNotEmpty(model.getProjectId())){
                wrapper.eq(DebugDetailedEntity::getProjectId,model.getProjectId());
            }
            if (ToolUtil.isNotEmpty(model.getUserId())){
                wrapper.eq(DebugDetailedEntity::getUserId,model.getUserId());
            }
            if (ToolUtil.isNotEmpty(model.getVersionId())){
                wrapper.eq(DebugDetailedEntity::getVersionId,model.getVersionId());
            }
            if (ToolUtil.isNotEmpty(model.getType())){
                wrapper.eq(DebugDetailedEntity::getType,model.getType());
            }
            if (ToolUtil.isNotEmpty(model.getPriority())){
                wrapper.eq(DebugDetailedEntity::getPriority,model.getPriority());
            }
            if (ToolUtil.isNotEmpty(model.getLevel())){
                wrapper.eq(DebugDetailedEntity::getLevel,model.getLevel());
            }
            if (ToolUtil.isNotEmpty(model.getKeyWork())){
                wrapper.eq(DebugDetailedEntity::getKeyWork,model.getKeyWork());
            }
            if (ToolUtil.isNotEmpty(model.getEndDate())){
                wrapper.eq(DebugDetailedEntity::getEndDate,model.getEndDate());
            }
            if (ToolUtil.isNotEmpty(model.getContent())){
                wrapper.eq(DebugDetailedEntity::getContent,model.getContent());
            }
            if (ToolUtil.isNotEmpty(model.getFileIds())){
                wrapper.eq(DebugDetailedEntity::getFileIds,model.getFileIds());
            }
            if (ToolUtil.isNotEmpty(model.getCreateBy())){
                wrapper.eq(DebugDetailedEntity::getCreateBy,model.getCreateBy());
            }
            if (ToolUtil.isNotEmpty(model.getCreateDate())){
                wrapper.eq(DebugDetailedEntity::getCreateDate,model.getCreateDate());
            }

            if (ToolUtil.isNotEmpty(model.getSolvePerson())){
                wrapper.eq(DebugDetailedEntity::getSolvePerson,model.getSolvePerson());
            }
            if (ToolUtil.isNotEmpty(model.getSolveTime())){
                wrapper.eq(DebugDetailedEntity::getSolveTime,model.getSolveTime());
            }
            if (ToolUtil.isNotEmpty(model.getFeedback())){
                wrapper.eq(DebugDetailedEntity::getFeedback,model.getFeedback());
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
    * @return List<DebugDetailedEntity>
    */
    public List<DebugDetailedEntity> selectList(DebugDetailedEntity model){
        return list(getWrapper(model));
    }

    public DebugDetailedEntity getOne(DebugDetailedEntity model){

        List<DebugDetailedEntity> entities = dao.selectListModel(model);
        if (entities.size() ==1){
            return entities.get(0);
        }else {
            return null;
        }
    }
    /**
    * <p>
    * 根据 entity 条件，查询全部记录（并翻页）
    * </p>
    *
    * @param pagination 分页查询条件
    * @param model   实体对象封装操作可以为 null）
    * @param wrapper   SQL包装
    * @return List<DebugDetailedEntity>
    */
    public List<DebugDetailedEntity> selectPage(Page pagination, DebugDetailedEntity model,Wrapper<DebugDetailedEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<DebugDetailedEntity>
    */
    public List<DebugDetailedEntity> selectPage(Page pagination,Wrapper<DebugDetailedEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
