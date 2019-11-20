package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysDictEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysDictDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-08-30 17:37:39
*/
@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysDictEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysDictEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysDictEntity>
    */
    public List<SysDictEntity> selectListModel(@Param("e") SysDictEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysDictEntity> 所有符合条件数据
    */
    public List<SysDictEntity> selectPage(@Param("p") Page pagination, @Param("e") SysDictEntity entity, @Param("ew") Wrapper<SysDictEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysDictEntity> 所有符合条件数据
    */
    public  List<SysDictEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysDictEntity> wrapper);
}
