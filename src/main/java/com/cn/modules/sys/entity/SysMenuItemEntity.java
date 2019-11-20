package com.cn.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import com.cn.common.entity.AbstractModel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
/**
* 菜单条件 entity 对象实体类
* @author zhangheng
* @date 2019-09-18 21:17:28
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu_item")
public class SysMenuItemEntity extends AbstractModel<SysMenuItemEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键ID
    */
    @Excel(name = "主键ID")
    @TableId(value ="id",type = IdType.ID_WORKER_STR)
    private String id;
    /**
    * 名称
    */
    @Excel(name = "名称")
    @TableField(value="NAME")
    private String name;
    /**
    * 排序
    */
    @Excel(name = "排序")
    @TableField(value="MENU_SORT")
    private Integer menuSort;
    /**
    * 系统id
    */
    @Excel(name = "系统id")
    @TableField(value="SYS_ID")
    private String sysId;
    /**
    * 权限标识
    */
    @Excel(name = "权限标识")
    @TableField(value="PERMISSION")
    private String permission;
    /**
    * MenuType
    */
    @Excel(name = "MenuType")
    @TableField(value="MENU_TYPE")
    private Integer menuType;
    /**
    * 连接href
    */
    @Excel(name = "连接href")
    @TableField(value="MEENU_HREF")
    private String meenuHref;
    /**
    * 评论
    */
    @Excel(name = "评论")
    @TableField(value="REMARKS")
    private String remarks;
    /**
    * 创建时间
    */
    @Excel(name = "创建时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="CREATE_time")
    private Date createTime;
    /**
    * 更新时间
    */
    @Excel(name = "更新时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="UPDATE_TIME")
    private Date updateTime;
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public void preInsert() {
        Sequence sequence = new Sequence(0, 0);
        this.id = String.valueOf(sequence.nextId());
    }
    @Override
    public void preUpdate() {

    }
    @JSONField(serialize = false)
    @Override
    public boolean getIsNewRecord() {
        return isNewRecord || StringUtils.isBlank(getId());
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}


