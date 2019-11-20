package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysRoleDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-08-30 17:28:20
*/
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysRoleEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysRoleEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysRoleEntity>
    */
    public List<SysRoleEntity> selectListModel(@Param("e") SysRoleEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysRoleEntity> 所有符合条件数据
    */
    public List<SysRoleEntity> selectPage(@Param("p") Page pagination, @Param("e") SysRoleEntity entity, @Param("ew") Wrapper<SysRoleEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysRoleEntity> 所有符合条件数据
    */
    public  List<SysRoleEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysRoleEntity> wrapper);
}
