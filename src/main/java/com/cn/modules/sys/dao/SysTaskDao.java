package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysTaskDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-08-09 15:29:50
*/
@Mapper
public interface SysTaskDao extends BaseMapper<SysTaskEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysTaskEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysTaskEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysTaskEntity>
    */
    public List<SysTaskEntity> selectListModel(@Param("e") SysTaskEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysTaskEntity> 所有符合条件数据
    */
    public List<SysTaskEntity> selectPage(@Param("p") Page pagination, @Param("e") SysTaskEntity entity, @Param("ew") Wrapper<SysTaskEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysTaskEntity> 所有符合条件数据
    */
    public  List<SysTaskEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysTaskEntity> wrapper);
}
