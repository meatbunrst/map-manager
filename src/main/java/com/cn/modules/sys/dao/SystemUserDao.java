package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SystemUserDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-04-30 14:43:35
*/
@Mapper
public interface SystemUserDao extends BaseMapper<SystemUserEntity>{



    /**
     *  查询用户的所有菜单ID
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);



    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     *  @return Integer
     */
    List<String> queryAllPerms(Long userId);
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SystemUserEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SystemUserEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SystemUserEntity>
    */
    public List<SystemUserEntity> selectListModel(@Param("e") SystemUserEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SystemUserEntity> 所有符合条件数据
    */
    public List<SystemUserEntity> selectPage(@Param("p") Page pagination, @Param("e") SystemUserEntity entity, @Param("ew") Wrapper<SystemUserEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SystemUserEntity> 所有符合条件数据
    */
    public  List<SystemUserEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SystemUserEntity> wrapper);
}
