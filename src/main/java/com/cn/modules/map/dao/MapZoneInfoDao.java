package com.cn.modules.map.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.modules.map.entity.MapZoneInfoEntity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
* MapZoneInfoDao 模型，对应的xml 的一些数据
*
* @author tianqian
* @date 2020-04-16 16:42:52
*/
@Mapper
public interface MapZoneInfoDao extends BaseMapper<MapZoneInfoEntity>{
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<MapZoneInfoEntity>
    */
    public List<MapZoneInfoEntity> selectListModel(@Param("e") MapZoneInfoEntity entity);

}
