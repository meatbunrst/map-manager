package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysMenuDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-07-17 22:44:40
*/
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity>{


    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList(@Param("userId") Long userId);


    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysMenuEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysMenuEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysMenuEntity>
    */
    public List<SysMenuEntity> selectListModel(@Param("e") SysMenuEntity entity);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param entity 实体对象
    * @param wrapper 查询条件
    * @return List<SysMenuEntity> 所有符合条件数据
    */
    public List<SysMenuEntity> selectPage(@Param("p") Page pagination, @Param("e") SysMenuEntity entity, @Param("ew") Wrapper<SysMenuEntity> wrapper);
    /**
    * 分页查询信息
    * @param pagination 分页信息
    * @param wrapper 查询条件
    * @return List<SysMenuEntity> 所有符合条件数据
    */
    public  List<SysMenuEntity> queryPage(@Param("p") Page pagination, @Param("ew") Wrapper<SysMenuEntity> wrapper);
}
