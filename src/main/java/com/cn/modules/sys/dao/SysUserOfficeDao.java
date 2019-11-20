package com.cn.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.modules.sys.entity.SysUserOfficeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* SysUserOfficeDao 模型，对应的xml 的一些数据
*
* @author zhangheng
* @date 2019-05-08 14:17:30
*/
@Mapper
public interface SysUserOfficeDao extends BaseMapper<SysUserOfficeEntity>{
    /**
    * 删除数据
    * @param entity
    * @return Integer
    */
    public Integer deleteByModel(@Param("e") SysUserOfficeEntity entity);

    /**
    * 统计数量
    * @param entity 实体对象
    * @return Integer
    */
    public Integer selectCountByModel(@Param("e") SysUserOfficeEntity entity);
    /**
    * 查询列表
    * @param entity 实体对象
    * @return  List<SysUserOfficeEntity>
    */
    public List<SysUserOfficeEntity> selectListModel(@Param("e") SysUserOfficeEntity entity);

}
