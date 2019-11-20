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
* bug详情 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:36:55
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("debug_detailed")
public class DebugDetailedEntity extends AbstractModel<DebugDetailedEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键ID
    */
    @Excel(name = "主键ID")
    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;
    /**
    * 标题
    */
    @Excel(name = "标题")
    @TableField(value="title")
    private String title;
    /**
    * 模板ID
    */
    @Excel(name = "模板ID")
    @TableField(value="module_id")
    private String moduleId;

    @TableField(exist = false)
    private String moduleName;
    /**
    * 项目ID
    */
    @Excel(name = "项目ID")
    @TableField(value="project_id")
    private Integer projectId;

    @TableField(exist = false)
    private String projectName;
    /**
    * 指派用户
    */
    @Excel(name = "指派用户")
    @TableField(value="user_id")
    private Long userId;

    @TableField(exist = false)
    private String userName;
    /**
    * 版本id
    */
    @Excel(name = "版本id")
    @TableField(value="version_id")
    private Integer versionId;
    /**
    * Type
    */
    @Excel(name = "类型")
    @TableField(value="type")
    private Integer type;
    /**
    * 优先级
    */
    @Excel(name = "优先级")
    @TableField(value="priority")
    private Integer priority;
    /**
    * 严重程度
    */
    @Excel(name = "严重程度")
    @TableField(value="level")
    private Integer level;
    /**
    * 关键词
    */
    @Excel(name = "关键词")
    @TableField(value="key_work")
    private String keyWork;
    /**
    * 截止日期
    */
    @Excel(name = "截止日期",exportFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    @TableField(value="end_date")
    private Date endDate;
    /**
    * 内容
    */
    @Excel(name = "内容")
    @TableField(value="content")
    private String content;
    /**
    * 附件ID
    */
    @Excel(name = "附件ID")
    @TableField(value="file_ids")
    private String fileIds;
    /**
    * 创建人
    */
    @Excel(name = "创建人")
    @TableField(value="create_by")
    private String createBy;
    /**
    * 创建日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="create_date")
    private Date createDate;


    @TableField(value="solve_person")
    private String solvePerson;
    /**
     * 解决时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="solve_time")
    private Date solveTime;
    /**
     * 反馈
     */
    @TableField(value="feedback")
    private String feedback;

    @TableField(exist = false)
    private String colorType;


    @TableField(exist = false)
    private String warnString;
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


