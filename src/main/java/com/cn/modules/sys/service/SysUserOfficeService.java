package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.common.service.AbstractService;
import com.cn.modules.sys.dao.SysUserOfficeDao;
import com.cn.modules.sys.entity.SysUserOfficeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 用户部门Service  业务接口
*
* @author zhangheng
* @date 2019-05-08 14:17:31
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SysUserOfficeService extends AbstractService<SysUserOfficeDao,SysUserOfficeEntity>{

    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysUserOfficeEntity model){
        return dao.selectCountByModel(model);
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysUserOfficeEntity model){
        return SqlHelper.delBool(dao.deleteByModel(model));
    }

    /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param model 实体对象封装操作类（可以为 null）
    * @return List<SysUserOfficeEntity>
    */
    public List<SysUserOfficeEntity> selectList(SysUserOfficeEntity model){
        return dao.selectListModel(model);
    }

}
