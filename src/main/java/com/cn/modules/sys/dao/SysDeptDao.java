package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysDeptDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2018-10-26 10:45:51
*/
@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity>{


    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysDeptEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysDeptEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysDeptEntity>
    */
    public List<SysDeptEntity> selectListModel(@Param("e") SysDeptEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysDeptEntity> 所有符合条件数据
    */
    public List<SysDeptEntity> selectPage(@Param("p") Page pagination, @Param("e") SysDeptEntity entity, @Param("ew") Wrapper<SysDeptEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysDeptEntity> 所有符合条件数据
    */
    public  List<SysDeptEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysDeptEntity> wrapper);
}
