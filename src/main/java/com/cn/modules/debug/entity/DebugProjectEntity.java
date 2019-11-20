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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
* 项目表 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:11:09
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("debug_project")
public class DebugProjectEntity extends AbstractModel<DebugProjectEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键id
    */
    @Excel(name = "主键id")
    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;
    /**
    * 项目名
    */
    @Excel(name = "项目名")
    @TableField(value="name")
    private String name;
    /**
    * 状态
    */
    @Excel(name = "状态")
    @TableField(value="status")
    private Integer status;
    /**
    * bug总数
    */
    @Excel(name = "bug总数")
    @TableField(value="sum_bug")
    private Integer sumBug;
    /**
    * 描述
    */
    @Excel(name = "描述")
    @TableField(value="marks")
    private String marks;
    /**
    * 文件ID字符串
    */
    @Excel(name = "文件ID字符串")
    @TableField(value="file_ids")
    private String fileIds;
    /**
    * 起始时间
    */
    @Excel(name = "起始时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="create_date")
    private Date createDate;
    /**
    * 结束时间
    */
    @Excel(name = "结束时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="end_date")
    private Date endDate;
    /**
    * 项目拥有人
    */
    @Excel(name = "项目拥有人")
    @TableField(value="own_person")
    private String ownPerson;
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


