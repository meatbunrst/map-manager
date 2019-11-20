package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.dao.BatchAttachDao;
import com.cn.modules.sys.entity.BatchAttachEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 基本附件表Service  业务接口
*
* @author zhangheng
* @date 2019-07-26 14:43:48
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class BatchAttachService extends AbstractService<BatchAttachDao,BatchAttachEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(BatchAttachEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(BatchAttachEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<BatchAttachEntity> getWrapper(BatchAttachEntity model){
        LambdaQueryWrapper<BatchAttachEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getBattchId())){
                    wrapper.eq(BatchAttachEntity::getBattchId,model.getBattchId());
                }
                if (ToolUtil.isNotEmpty(model.getModuleRecordId())){
                    wrapper.eq(BatchAttachEntity::getModuleRecordId,model.getModuleRecordId());
                }
                if (ToolUtil.isNotEmpty(model.getModuleType())){
                    wrapper.eq(BatchAttachEntity::getModuleType,model.getModuleType());
                }
                if (ToolUtil.isNotEmpty(model.getBusinessType())){
                    wrapper.eq(BatchAttachEntity::getBusinessType,model.getBusinessType());
                }
                if (ToolUtil.isNotEmpty(model.getFileType())){
                    wrapper.eq(BatchAttachEntity::getFileType,model.getFileType());
                }
                if (ToolUtil.isNotEmpty(model.getAttachDesc())){
                    wrapper.eq(BatchAttachEntity::getAttachDesc,model.getAttachDesc());
                }
                if (ToolUtil.isNotEmpty(model.getUploadFileName())){
                    wrapper.eq(BatchAttachEntity::getUploadFileName,model.getUploadFileName());
                }
                if (ToolUtil.isNotEmpty(model.getLocalSavePath())){
                    wrapper.eq(BatchAttachEntity::getLocalSavePath,model.getLocalSavePath());
                }
                if (ToolUtil.isNotEmpty(model.getFsBasePath())){
                    wrapper.eq(BatchAttachEntity::getFsBasePath,model.getFsBasePath());
                }
                if (ToolUtil.isNotEmpty(model.getFsDatePath())){
                    wrapper.eq(BatchAttachEntity::getFsDatePath,model.getFsDatePath());
                }
                if (ToolUtil.isNotEmpty(model.getFsFileName())){
                    wrapper.eq(BatchAttachEntity::getFsFileName,model.getFsFileName());
                }
                if (ToolUtil.isNotEmpty(model.getUploadUserAccount())){
                    wrapper.eq(BatchAttachEntity::getUploadUserAccount,model.getUploadUserAccount());
                }
                if (ToolUtil.isNotEmpty(model.getUploadDate())){
                    wrapper.eq(BatchAttachEntity::getUploadDate,model.getUploadDate());
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
    * @return List<BatchAttachEntity>
    */
    public List<BatchAttachEntity> selectList(BatchAttachEntity model){
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
    * @return List<BatchAttachEntity>
    */
    public List<BatchAttachEntity> selectPage(Page pagination, BatchAttachEntity model,QueryWrapper<BatchAttachEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<BatchAttachEntity>
    */
    public List<BatchAttachEntity> selectPage(Page pagination,QueryWrapper<BatchAttachEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
