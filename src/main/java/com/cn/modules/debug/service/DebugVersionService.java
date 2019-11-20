package com.cn.modules.debug.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.debug.dao.DebugVersionDao;
import com.cn.modules.debug.entity.DebugVersionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 版本迭代Service  业务接口
 *
 * @author zhangheng
 * @date 2019-07-25 10:31:08
 */
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class DebugVersionService extends AbstractService<DebugVersionDao, DebugVersionEntity>{

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    public Integer selectCount(DebugVersionEntity model){
        return count(getWrapper(model));
    }

    /**
     * 根据 model 条件，删除
     *
     * @param model 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(DebugVersionEntity model){
        return remove(getWrapper(model));
    }

    /**
     * 根据 model 条件，生成LambdaQueryWrapper
     *
     * @param model 实体对象
     * @return LambdaQueryWrapper
     */
    public LambdaQueryWrapper<DebugVersionEntity> getWrapper(DebugVersionEntity model){
        LambdaQueryWrapper<DebugVersionEntity> wrapper = new LambdaQueryWrapper<>();
        if (model != null){
            if (ToolUtil.isNotEmpty(model.getId())){
                wrapper.eq(DebugVersionEntity::getId,model.getId());
            }
            if (ToolUtil.isNotEmpty(model.getName())){
                wrapper.eq(DebugVersionEntity::getName,model.getName());
            }
            if (ToolUtil.isNotEmpty(model.getCode())){
                wrapper.eq(DebugVersionEntity::getCode,model.getCode());
            }
            if (ToolUtil.isNotEmpty(model.getStartDate())){
                wrapper.eq(DebugVersionEntity::getStartDate,model.getStartDate());
            }
            if (ToolUtil.isNotEmpty(model.getEndDate())){
                wrapper.eq(DebugVersionEntity::getEndDate,model.getEndDate());
            }
            if (ToolUtil.isNotEmpty(model.getSumWorkday())){
                wrapper.eq(DebugVersionEntity::getSumWorkday,model.getSumWorkday());
            }
            if (ToolUtil.isNotEmpty(model.getStatus())){
                wrapper.eq(DebugVersionEntity::getStatus,model.getStatus());
            }
            if (ToolUtil.isNotEmpty(model.getCreateDate())){
                wrapper.eq(DebugVersionEntity::getCreateDate,model.getCreateDate());
            }
            if (ToolUtil.isNotEmpty(model.getCreateBy())){
                wrapper.eq(DebugVersionEntity::getCreateBy,model.getCreateBy());
            }
            if (ToolUtil.isNotEmpty(model.getProjectId())){
                wrapper.eq(DebugVersionEntity::getProjectId,model.getProjectId());
            }
            if (ToolUtil.isNotEmpty(model.getDescribe())){
                wrapper.eq(DebugVersionEntity::getDescribe,model.getDescribe());
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
     * @return List<DebugVersionEntity>
     */
    public List<DebugVersionEntity> selectList(DebugVersionEntity model){
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
     * @return List<DebugVersionEntity>
     */
    public List<DebugVersionEntity> selectPage(Page pagination, DebugVersionEntity model,QueryWrapper<DebugVersionEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param pagination 分页查询条件
     * @param wrapper   SQL包装
     * @return List<DebugVersionEntity>
     */
    public List<DebugVersionEntity> selectPage(Page pagination,QueryWrapper<DebugVersionEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
