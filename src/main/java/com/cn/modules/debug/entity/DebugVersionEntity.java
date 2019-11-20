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
* 版本迭代 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:31:11
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("debug_version")
public class DebugVersionEntity extends AbstractModel<DebugVersionEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 编号
    */
    @Excel(name = "编号")
    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;
    /**
    * 迭代名称
    */
    @Excel(name = "迭代名称")
    @TableField(value="name")
    private String name;
    /**
    * 迭代代号
    */
    @Excel(name = "迭代代号")
    @TableField(value="code")
    private String code;
    /**
    * 开始日期
    */
    @Excel(name = "开始日期",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="start_date")
    private Date startDate;
    /**
    * 结束日期
    */
    @Excel(name = "结束日期",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="end_date")
    private Date endDate;
    /**
    * 可用工作日
    */
    @Excel(name = "可用工作日")
    @TableField(value="sum_workday")
    private Integer sumWorkday;
    /**
    * 迭代类型
    */
    @Excel(name = "迭代类型")
    @TableField(value="status")
    private Integer status;
    /**
    * 创建时间
    */
    @Excel(name = "创建时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="create_date")
    private Date createDate;
    /**
    * 创建人
    */
    @Excel(name = "创建人")
    @TableField(value="create_by")
    private String createBy;
    /**
    * 关联项目ID
    */
    @Excel(name = "关联项目ID")
    @TableField(value="Project_id")
    private Integer projectId;
    /**
    * 描述
    */
    @Excel(name = "描述")
    @TableField(value="describe")
    private String describe;
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


