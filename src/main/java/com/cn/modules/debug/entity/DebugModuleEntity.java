package com.cn.modules.debug.entity;

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
* 模板表 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:23:02
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("debug_module")
public class DebugModuleEntity extends AbstractModel<DebugModuleEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * Id
    */
    @Excel(name = "Id")
    @TableId(value ="ID",type = IdType.ID_WORKER_STR)
    private String id;
    /**
    * Num
    */
    @Excel(name = "Num")
    @TableField(value="NUM")
    private Integer num;
    /**
    * Pid
    */
    @Excel(name = "Pid")
    @TableField(value="PID")
    private Integer pid;
    /**
    * Pids
    */
    @Excel(name = "Pids")
    @TableField(value="PIDS")
    private String pids;
    /**
    * 简称
    */
    @Excel(name = "简称")
    @TableField(value="SIMPLENAME")
    private String simplename;
    /**
    * 全称
    */
    @Excel(name = "全称")
    @TableField(value="FULLNAME")
    private String fullname;
    /**
    * Tips
    */
    @Excel(name = "Tips")
    @TableField(value="TIPS")
    private String tips;
    /**
    * Version
    */
    @Excel(name = "Version")
    @TableField(value="VERSION")
    private Integer version;

    /**
     * 项目ID
     */
    @Excel(name = "项目ID")
    @TableField(value="project_id")
    private Integer projectId;
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public void preInsert() {
        Sequence sequence = new Sequence(0, 0);
//        this.id = String.valueOf(sequence.nextId());
    }
    @Override
    public void preUpdate() {

    }
    @JSONField(serialize = false)
    @Override
    public boolean getIsNewRecord() {
        return isNewRecord;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}


