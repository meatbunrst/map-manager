package com.cn.modules.debug.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.debug.entity.DebugDetailedEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* DebugDetailedDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-05-25 23:36:55
*/
@Mapper
public interface DebugDetailedDao extends BaseMapper<DebugDetailedEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") DebugDetailedEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") DebugDetailedEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<DebugDetailedEntity>
    */
    public List<DebugDetailedEntity> selectListModel(@Param("e") DebugDetailedEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<DebugDetailedEntity> 所有符合条件数据
    */
    public List<DebugDetailedEntity> selectPage(@Param("p") Page pagination, @Param("e") DebugDetailedEntity entity, @Param("ew") Wrapper<DebugDetailedEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<DebugDetailedEntity> 所有符合条件数据
    */
    public  List<DebugDetailedEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<DebugDetailedEntity> wrapper);
}
