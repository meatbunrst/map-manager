package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysMenuItemEntity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
* SysMenuItemDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-09-18 21:17:28
*/
@Mapper
public interface SysMenuItemDao extends BaseMapper<SysMenuItemEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysMenuItemEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysMenuItemEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysMenuItemEntity>
    */
    public List<SysMenuItemEntity> selectListModel(@Param("e") SysMenuItemEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysMenuItemEntity> 所有符合条件数据
    */
    public List<SysMenuItemEntity> selectPage(@Param("p") Page pagination, @Param("e") SysMenuItemEntity entity, @Param("ew") Wrapper<SysMenuItemEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysMenuItemEntity> 所有符合条件数据
    */
    public  List<SysMenuItemEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysMenuItemEntity> wrapper);
}
