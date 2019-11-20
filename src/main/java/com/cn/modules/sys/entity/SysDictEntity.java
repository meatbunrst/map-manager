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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
/**
* 字典表 entity 对象实体类
* @author zhangheng
* @date 2019-08-30 17:37:39
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict")
public class SysDictEntity extends AbstractModel<SysDictEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @Excel(name = "主键")
    @TableId(value ="id",type = IdType.ID_WORKER_STR)
    private String id;
    /**
    * 字典名
    */
    @Excel(name = "字典名")
    @TableField(value="dict_name")
    private String dictName;
    /**
    * 描述
    */
    @Excel(name = "描述")
    @TableField(value="remark")
    private String remark;
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


