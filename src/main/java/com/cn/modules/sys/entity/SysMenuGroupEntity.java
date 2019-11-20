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
* 代码生成组 entity 对象实体类
* @author zhangheng
* @date 2019-09-18 21:17:38
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu_group")
public class SysMenuGroupEntity extends AbstractModel<SysMenuGroupEntity> {
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
    * 简称
    */
    @Excel(name = "简称")
    @TableField(value="CODE")
    private String code;
    /**
    * 创建日期
    */
    @Excel(name = "创建日期",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="CREATE_time")
    private Date createTime;
    /**
    * 更新日期
    */
    @Excel(name = "更新日期",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="UPDATE_time")
    private Date updateTime;
    /**
    * 评论
    */
    @Excel(name = "评论")
    @TableField(value="REMARKS")
    private String remarks;
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


