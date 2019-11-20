package com.cn.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.cn.common.entity.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
/**
* 系统配置信息表 entity 对象实体类
* @author zhangheng
* @date 2019-08-30 17:40:18
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict_detailed")
public class SysDictDetailedEntity extends AbstractModel<SysDictDetailedEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * Id
    */
    @Excel(name = "Id")
    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;
    /**
    * key
    */
    @Excel(name = "key")
    @TableField(value="param_key")
    private String paramKey;
    /**
    * value
    */
    @Excel(name = "value")
    @TableField(value="param_value")
    private String paramValue;
    /**
    * 状态   0：隐藏   1：显示
    */
    @Excel(name = "状态   0：隐藏   1：显示")
    @TableField(value="status")
    private Integer status;
    /**
    * 备注
    */
    @Excel(name = "备注")
    @TableField(value="remark")
    private String remark;
    /**
    * 标签
    */
    @Excel(name = "标签")
    @TableField(value="label")
    private String label;
    /**
    * 标签ID
    */
    @Excel(name = "标签ID")
    @TableField(value="label_id")
    private String labelId;
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public void preInsert() {
        Sequence sequence = new Sequence(0, 0);
//        this.id = sequence.nextId();
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


