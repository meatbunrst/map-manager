package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysDictDetailedEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysDictDetailedDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-08-30 17:40:18
*/
@Mapper
public interface SysDictDetailedDao extends BaseMapper<SysDictDetailedEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysDictDetailedEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysDictDetailedEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysDictDetailedEntity>
    */
    public List<SysDictDetailedEntity> selectListModel(@Param("e") SysDictDetailedEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysDictDetailedEntity> 所有符合条件数据
    */
    public List<SysDictDetailedEntity> selectPage(@Param("p") Page pagination, @Param("e") SysDictDetailedEntity entity, @Param("ew") Wrapper<SysDictDetailedEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysDictDetailedEntity> 所有符合条件数据
    */
    public  List<SysDictDetailedEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysDictDetailedEntity> wrapper);
}
