package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysNotifyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysNotifyDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-08-05 22:52:03
*/
@Mapper
public interface SysNotifyDao extends BaseMapper<SysNotifyEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysNotifyEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysNotifyEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysNotifyEntity>
    */
    public List<SysNotifyEntity> selectListModel(@Param("e") SysNotifyEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysNotifyEntity> 所有符合条件数据
    */
    public List<SysNotifyEntity> selectPage(@Param("p") Page pagination, @Param("e") SysNotifyEntity entity, @Param("ew") Wrapper<SysNotifyEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysNotifyEntity> 所有符合条件数据
    */
    public  List<SysNotifyEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysNotifyEntity> wrapper);
}
