package com.cn.modules.debug.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.debug.dao.DebugModuleDao;
import com.cn.modules.debug.entity.DebugModuleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 模板表Service  业务接口
 *
 * @author zhangheng
 * @date 2019-05-25 23:23:02
 */
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class DebugModuleService extends AbstractService<DebugModuleDao, DebugModuleEntity>{


    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    public Integer selectCount(DebugModuleEntity model){
        return count(getWrapper(model));
    }

    /**
     * 根据 model 条件，删除
     *
     * @param model 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(DebugModuleEntity model){
        return remove(getWrapper(model));
    }

    /**
     * 根据 model 条件，生成LambdaQueryWrapper
     *
     * @param model 实体对象
     * @return LambdaQueryWrapper
     */
    public LambdaQueryWrapper<DebugModuleEntity> getWrapper(DebugModuleEntity model){
        LambdaQueryWrapper<DebugModuleEntity> wrapper = new LambdaQueryWrapper<>();
        if (model != null){
            if (ToolUtil.isNotEmpty(model.getId())){
                wrapper.eq(DebugModuleEntity::getId,model.getId());
            }
            if (ToolUtil.isNotEmpty(model.getNum())){
                wrapper.eq(DebugModuleEntity::getNum,model.getNum());
            }
            if (ToolUtil.isNotEmpty(model.getPid())){
                wrapper.eq(DebugModuleEntity::getPid,model.getPid());
            }
            if (ToolUtil.isNotEmpty(model.getPids())){
                wrapper.eq(DebugModuleEntity::getPids,model.getPids());
            }
            if (ToolUtil.isNotEmpty(model.getSimplename())){
                wrapper.eq(DebugModuleEntity::getSimplename,model.getSimplename());
            }
            if (ToolUtil.isNotEmpty(model.getFullname())){
                wrapper.eq(DebugModuleEntity::getFullname,model.getFullname());
            }
            if (ToolUtil.isNotEmpty(model.getTips())){
                wrapper.eq(DebugModuleEntity::getTips,model.getTips());
            }
            if (ToolUtil.isNotEmpty(model.getVersion())){
                wrapper.eq(DebugModuleEntity::getVersion,model.getVersion());
            }
            if (ToolUtil.isNotEmpty(model.getProjectId())){
                wrapper.eq(DebugModuleEntity::getProjectId,model.getProjectId());
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
     * @return List<DebugModuleEntity>
     */
    public List<DebugModuleEntity> selectList(DebugModuleEntity model){
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
     * @return List<DebugModuleEntity>
     */
    public List<DebugModuleEntity> selectPage(Page pagination, DebugModuleEntity model,Wrapper<DebugModuleEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param pagination 分页查询条件
     * @param wrapper   SQL包装
     * @return List<DebugModuleEntity>
     */
    public List<DebugModuleEntity> selectPage(Page pagination,Wrapper<DebugModuleEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
