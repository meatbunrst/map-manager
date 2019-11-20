package com.cn.modules.debug.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.debug.dao.DebugProjectDao;
import com.cn.modules.debug.entity.DebugProjectEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 项目表Service  业务接口
 *
 * @author zhangheng
 * @date 2019-07-25 10:30:31
 */
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class DebugProjectService extends AbstractService<DebugProjectDao, DebugProjectEntity>{

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    public Integer selectCount(DebugProjectEntity model){
        return count(getWrapper(model));
    }

    /**
     * 根据 model 条件，删除
     *
     * @param model 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(DebugProjectEntity model){
        return remove(getWrapper(model));
    }

    /**
     * 根据 model 条件，生成LambdaQueryWrapper
     *
     * @param model 实体对象
     * @return LambdaQueryWrapper
     */
    public LambdaQueryWrapper<DebugProjectEntity> getWrapper(DebugProjectEntity model){
        LambdaQueryWrapper<DebugProjectEntity> wrapper = new LambdaQueryWrapper<>();
        if (model != null){
            if (ToolUtil.isNotEmpty(model.getId())){
                wrapper.eq(DebugProjectEntity::getId,model.getId());
            }
            if (ToolUtil.isNotEmpty(model.getName())){
                wrapper.eq(DebugProjectEntity::getName,model.getName());
            }
            if (ToolUtil.isNotEmpty(model.getStatus())){
                wrapper.eq(DebugProjectEntity::getStatus,model.getStatus());
            }
            if (ToolUtil.isNotEmpty(model.getSumBug())){
                wrapper.eq(DebugProjectEntity::getSumBug,model.getSumBug());
            }
            if (ToolUtil.isNotEmpty(model.getMarks())){
                wrapper.eq(DebugProjectEntity::getMarks,model.getMarks());
            }
            if (ToolUtil.isNotEmpty(model.getFileIds())){
                wrapper.eq(DebugProjectEntity::getFileIds,model.getFileIds());
            }
            if (ToolUtil.isNotEmpty(model.getCreateDate())){
                wrapper.eq(DebugProjectEntity::getCreateDate,model.getCreateDate());
            }
            if (ToolUtil.isNotEmpty(model.getEndDate())){
                wrapper.eq(DebugProjectEntity::getEndDate,model.getEndDate());
            }
            if (ToolUtil.isNotEmpty(model.getOwnPerson())){
                wrapper.eq(DebugProjectEntity::getOwnPerson,model.getOwnPerson());
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
     * @return List<DebugProjectEntity>
     */
    public List<DebugProjectEntity> selectList(DebugProjectEntity model){
        return list(getWrapper(model));
    }
    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param pagination 分页查询条件
     * @param model   实体对象封装操作可以为 null）
     * @param wrapper   SQL包装
     * @return List<DebugProjectEntity>
     */
    public List<DebugProjectEntity> selectPage(Page pagination, DebugProjectEntity model,QueryWrapper<DebugProjectEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param pagination 分页查询条件
     * @param wrapper   SQL包装
     * @return List<DebugProjectEntity>
     */
    public List<DebugProjectEntity> selectPage(Page pagination,QueryWrapper<DebugProjectEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
