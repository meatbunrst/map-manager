package com.cn.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.cn.common.entity.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* 日志 entity 对象实体类
* @author zhangheng
* @date 2019-04-28 13:47:06
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("SYS_LOG")
public class SysLogEntity extends AbstractModel<SysLogEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 创建时间
    */
    @Excel(name = "创建时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="CREATE_DATE",fill = FieldFill.INSERT)
    private Date createDate;
    /**
    * 主键
    */
    @Excel(name = "主键")
    @TableId(value ="ID",type = IdType.ID_WORKER)
    private Long id;
    /**
    * 用户名
    */
    @Excel(name = "用户名")
    @TableField(value="USERNAME")
    private String username;
    /**
    * 用户操作
    */
    @Excel(name = "用户操作")
    @TableField(value="OPERATION")
    private String operation;
    /**
    * 请求方法
    */
    @Excel(name = "请求方法")
    @TableField(value="METHOD")
    private String method;
    /**
    * 请求参数
    */
    @Excel(name = "请求参数")
    @TableField(value="PARAMS")
    private String params;
    /**
    * 执行时长
    */
    @Excel(name = "执行时长")
    @TableField(value="TIME")
    private Long time;
    /**
    * IP地址
    */
    @Excel(name = "IP地址")
    @TableField(value="IP")
    private String ip;
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public void preInsert() {
        Sequence sequence = new Sequence(0, 0);
        this.id = sequence.nextId();
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


