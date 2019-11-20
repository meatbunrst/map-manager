package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.dao.SysNotifyDao;
import com.cn.modules.sys.entity.SysNotifyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 通知通告Service  业务接口
*
* @author zhangheng
* @date 2019-08-05 22:52:03
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysNotifyService extends AbstractService<SysNotifyDao,SysNotifyEntity> {

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysNotifyEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysNotifyEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysNotifyEntity> getWrapper(SysNotifyEntity model){
        LambdaQueryWrapper<SysNotifyEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getId())){
                    wrapper.eq(SysNotifyEntity::getId,model.getId());
                }
                if (ToolUtil.isNotEmpty(model.getType())){
                    wrapper.eq(SysNotifyEntity::getType,model.getType());
                }
                if (ToolUtil.isNotEmpty(model.getTitle())){
                    wrapper.eq(SysNotifyEntity::getTitle,model.getTitle());
                }
                if (ToolUtil.isNotEmpty(model.getContent())){
                    wrapper.eq(SysNotifyEntity::getContent,model.getContent());
                }
                if (ToolUtil.isNotEmpty(model.getFiles())){
                    wrapper.eq(SysNotifyEntity::getFiles,model.getFiles());
                }
                if (ToolUtil.isNotEmpty(model.getStatus())){
                    wrapper.eq(SysNotifyEntity::getStatus,model.getStatus());
                }
                if (ToolUtil.isNotEmpty(model.getCreateBy())){
                    wrapper.eq(SysNotifyEntity::getCreateBy,model.getCreateBy());
                }
                if (ToolUtil.isNotEmpty(model.getCreateDate())){
                    wrapper.eq(SysNotifyEntity::getCreateDate,model.getCreateDate());
                }
                if (ToolUtil.isNotEmpty(model.getAttachIds())){
                    wrapper.eq(SysNotifyEntity::getAttachIds,model.getAttachIds());
                }
                if (ToolUtil.isNotEmpty(model.getUserType())){
                    wrapper.eq(SysNotifyEntity::getUserType,model.getUserType());
                }
                if (ToolUtil.isNotEmpty(model.getInfoIds())){
                    wrapper.eq(SysNotifyEntity::getInfoIds,model.getInfoIds());
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
    * @return List<SysNotifyEntity>
    */
    public List<SysNotifyEntity> selectList(SysNotifyEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysNotifyEntity> selectPage(Page pagination,SysNotifyEntity model){
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
    * @return List<SysNotifyEntity>
    */
    public List<SysNotifyEntity> selectPage(Page pagination, SysNotifyEntity model,QueryWrapper<SysNotifyEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysNotifyEntity>
    */
    public List<SysNotifyEntity> selectPage(Page pagination,QueryWrapper<SysNotifyEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
