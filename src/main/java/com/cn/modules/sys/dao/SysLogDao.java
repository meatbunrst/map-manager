package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysLogDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-04-28 13:47:03
*/
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysLogEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysLogEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysLogEntity>
    */
    public List<SysLogEntity> selectListModel(@Param("e") SysLogEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysLogEntity> 所有符合条件数据
    */
    public List<SysLogEntity> selectPage(@Param("p") Page pagination, @Param("e") SysLogEntity entity, @Param("ew") Wrapper<SysLogEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysLogEntity> 所有符合条件数据
    */
    public  List<SysLogEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysLogEntity> wrapper);
}
