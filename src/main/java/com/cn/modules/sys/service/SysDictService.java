package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.dao.SysDictDao;
import com.cn.modules.sys.entity.SysDictEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 字典表Service  业务接口
*
* @author zhangheng
* @date 2019-08-30 17:37:39
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysDictService extends AbstractService<SysDictDao,SysDictEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysDictEntity model){
        return count(getWrapper(model));
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysDictEntity model){
        return remove(getWrapper(model));
    }

    /**
    * 根据 model 条件，生成LambdaQueryWrapper
    *
    * @param model 实体对象
    * @return LambdaQueryWrapper
    */
    public LambdaQueryWrapper<SysDictEntity> getWrapper(SysDictEntity model){
        LambdaQueryWrapper<SysDictEntity> wrapper = new LambdaQueryWrapper<>();
            if (model != null){
                if (ToolUtil.isNotEmpty(model.getId())){
                    wrapper.eq(SysDictEntity::getId,model.getId());
                }
                if (ToolUtil.isNotEmpty(model.getDictName())){
                    wrapper.eq(SysDictEntity::getDictName,model.getDictName());
                }
                if (ToolUtil.isNotEmpty(model.getRemark())){
                    wrapper.eq(SysDictEntity::getRemark,model.getRemark());
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
    * @return List<SysDictEntity>
    */
    public List<SysDictEntity> selectList(SysDictEntity model){
        return list(getWrapper(model));
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param model   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SysDictEntity> selectPage(Page pagination,SysDictEntity model){
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
    * @return List<SysDictEntity>
    */
    public List<SysDictEntity> selectPage(Page pagination, SysDictEntity model,QueryWrapper<SysDictEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysDictEntity>
    */
    public List<SysDictEntity> selectPage(Page pagination,QueryWrapper<SysDictEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

}
