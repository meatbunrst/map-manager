package com.cn.modules.debug.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.debug.entity.DebugProjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* DebugProjectDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-05-25 23:11:08
*/
@Mapper
public interface DebugProjectDao extends BaseMapper<DebugProjectEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") DebugProjectEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") DebugProjectEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<DebugProjectEntity>
    */
    public List<DebugProjectEntity> selectListModel(@Param("e") DebugProjectEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<DebugProjectEntity> 所有符合条件数据
    */
    public List<DebugProjectEntity> selectPage(@Param("p") Page pagination, @Param("e") DebugProjectEntity entity, @Param("ew") Wrapper<DebugProjectEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<DebugProjectEntity> 所有符合条件数据
    */
    public  List<DebugProjectEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<DebugProjectEntity> wrapper);
}
