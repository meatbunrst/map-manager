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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
* 通知通告 entity 对象实体类
* @author zhangheng
* @date 2019-08-05 22:52:03
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_notify")
public class SysNotifyEntity extends AbstractModel<SysNotifyEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @Excel(name = "主键")
    @TableId(value ="ID",type = IdType.ID_WORKER_STR)
    private String id;
    /**
    * 类型
    */
    @Excel(name = "类型")
    @TableField(value="TYPE")
    private String type;
    /**
    * 标题
    */
    @Excel(name = "标题")
    @TableField(value="TITLE")
    private String title;
    /**
    * 内容
    */
    @Excel(name = "内容")
    @TableField(value="CONTENT")
    private String content;
    /**
    * 文件
    */
    @Excel(name = "文件")
    @TableField(value="FILES")
    private String files;
    /**
    * 状态
    */
    @Excel(name = "状态")
    @TableField(value="STATUS")
    private Integer status;
    /**
    * 创建者姓名
    */
    @Excel(name = "创建者姓名")
    @TableField(value="CREATE_BY")
    private String createBy;
    /**
    * 创建时间
    */
    @Excel(name = "创建时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="CREATE_DATE")
    private Date createDate;
    /**
    * 附件相关
    */
    @Excel(name = "附件相关")
    @TableField(value="ATTACH_IDS")
    private String attachIds;
    /**
    * 用户类型
    */
    @Excel(name = "用户类型")
    @TableField(value="USER_TYPE")
    private String userType;
    /**
    * 通知的用户信息
    */
    @Excel(name = "通知的用户信息")
    @TableField(value="INFO_IDS")
    private String infoIds;
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


