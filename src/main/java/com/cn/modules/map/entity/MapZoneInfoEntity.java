package com.cn.modules.map.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.math.BigDecimal;

import com.cn.common.entity.AbstractModel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
/**
* 区域信息描边表 entity 对象实体类
* @author tianqian
* @date 2020-04-16 16:42:52
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("map_zone_info")
public class MapZoneInfoEntity extends AbstractModel<MapZoneInfoEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @Excel(name = "主键")
    @TableId(value =" ID",type = IdType.ID_WORKER)
    private Integer id;
    /**
    * 地区id
    */
    @Excel(name = "地区id")
    @TableField(value="ZONE_ID")
    private String zoneId;
    /**
    * 地区名称
    */
    @Excel(name = "地区名称")
    @TableField(value="ZONE_NAME")
    private String zoneName;
    /**
    * 层级(0
    */
    @Excel(name = "层级(0")
    @TableField(value="ZONE_TREE")
    private Integer zoneTree;
    /**
    * 上级地址编码
    */
    @Excel(name = "上级地址编码")
    @TableField(value="PID")
    private String pid;
    /**
    * 描边点集
    */
    @Excel(name = "描边点集")
    @TableField(value="PATH")
    private String path;
    /**
    * 坐标经度
    */
    @Excel(name = "坐标经度")
    @TableField(value="LON")
    private String lon;
    /**
    * 坐标纬度
    */
    @Excel(name = "坐标纬度")
    @TableField(value="LAT")
    private String lat;
    /**
    * 地图默认放大层级
    */
    @Excel(name = "地图默认放大层级")
    @TableField(value="ZOOM")
    private Integer zoom;
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public void preInsert() {

    }
    @Override
    public void preUpdate() {

    }
    @JSONField(serialize = false)
    @Override
    public boolean getIsNewRecord() {
        return isNewRecord || getId() == null;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}


