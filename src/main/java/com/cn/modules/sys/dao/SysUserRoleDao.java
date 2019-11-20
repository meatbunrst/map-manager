package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysUserRoleDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-05-14 17:37:20
*/
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysUserRoleEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysUserRoleEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysUserRoleEntity>
    */
    public List<SysUserRoleEntity> selectListModel(@Param("e") SysUserRoleEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysUserRoleEntity> 所有符合条件数据
    */
    public List<SysUserRoleEntity> selectPage(@Param("p") Page pagination, @Param("e") SysUserRoleEntity entity, @Param("ew") Wrapper<SysUserRoleEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysUserRoleEntity> 所有符合条件数据
    */
    public  List<SysUserRoleEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysUserRoleEntity> wrapper);

    List<Long> queryRoleIdList(Long userId);

    int deleteBatch(Long[] roleIds);
}
