package com.cn.modules.debug.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.debug.entity.DebugVersionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* DebugVersionDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-05-25 23:31:11
*/
@Mapper
public interface DebugVersionDao extends BaseMapper<DebugVersionEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") DebugVersionEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") DebugVersionEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<DebugVersionEntity>
    */
    public List<DebugVersionEntity> selectListModel(@Param("e") DebugVersionEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<DebugVersionEntity> 所有符合条件数据
    */
    public List<DebugVersionEntity> selectPage(@Param("p") Page pagination, @Param("e") DebugVersionEntity entity, @Param("ew") Wrapper<DebugVersionEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<DebugVersionEntity> 所有符合条件数据
    */
    public  List<DebugVersionEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<DebugVersionEntity> wrapper);
}
