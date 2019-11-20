package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.BatchAttachEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* BatchAttachDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-07-26 14:43:48
*/
@Mapper
public interface BatchAttachDao extends BaseMapper<BatchAttachEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") BatchAttachEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") BatchAttachEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<BatchAttachEntity>
    */
    public List<BatchAttachEntity> selectListModel(@Param("e") BatchAttachEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<BatchAttachEntity> 所有符合条件数据
    */
    public List<BatchAttachEntity> selectPage(@Param("p") Page pagination, @Param("e") BatchAttachEntity entity, @Param("ew") Wrapper<BatchAttachEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<BatchAttachEntity> 所有符合条件数据
    */
    public  List<BatchAttachEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<BatchAttachEntity> wrapper);
}
