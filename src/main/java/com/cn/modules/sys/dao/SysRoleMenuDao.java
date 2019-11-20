package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysRoleMenuEntity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
* SysRoleMenuDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-09-06 15:20:10
*/
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity>{


    /**
     * 根据角色ID，获取菜单ID列表
     * @param roleId
     * @return List<Long>
     */
    List<Long> queryMenuIdList(Long roleId);


    /**
     * 根据角色ID数组，批量删除
     * @param roleIds
     * @return
     */
    int deleteBatch(Long[] roleIds);
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysRoleMenuEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysRoleMenuEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysRoleMenuEntity>
    */
    public List<SysRoleMenuEntity> selectListModel(@Param("e") SysRoleMenuEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysRoleMenuEntity> 所有符合条件数据
    */
    public List<SysRoleMenuEntity> selectPage(@Param("p") Page pagination, @Param("e") SysRoleMenuEntity entity, @Param("ew") Wrapper<SysRoleMenuEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysRoleMenuEntity> 所有符合条件数据
    */
    public  List<SysRoleMenuEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysRoleMenuEntity> wrapper);
}
