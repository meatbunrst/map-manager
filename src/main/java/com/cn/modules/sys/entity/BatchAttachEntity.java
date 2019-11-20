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
* 基本附件表 entity 对象实体类
* @author zhangheng
* @date 2019-07-26 14:58:08
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("batch_attach")
public class BatchAttachEntity extends AbstractModel<BatchAttachEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 附件ID
    */
    @Excel(name = "附件ID")
    @TableId(value ="BATTCH_ID",type = IdType.ID_WORKER_STR)
    private String battchId;
    /**
    * 关联的模块记录ID
    */
    @Excel(name = "关联的模块记录ID")
    @TableField(value="MODULE_RECORD_ID")
    private String moduleRecordId;
    /**
    * 模块类型
    */
    @Excel(name = "模块类型")
    @TableField(value="MODULE_TYPE")
    private String moduleType;
    /**
    * 业务类型
    */
    @Excel(name = "业务类型")
    @TableField(value="BUSINESS_TYPE")
    private String businessType;
    /**
    * 文件类型
    */
    @Excel(name = "文件类型")
    @TableField(value="FILE_TYPE")
    private String fileType;
    /**
    * 文件描述
    */
    @Excel(name = "文件描述")
    @TableField(value="ATTACH_DESC")
    private String attachDesc;
    /**
    * 上传文件名称
    */
    @Excel(name = "上传文件名称")
    @TableField(value="UPLOAD_FILE_NAME")
    private String uploadFileName;
    /**
    * 本地存放路径
    */
    @Excel(name = "本地存放路径")
    @TableField(value="LOCAL_SAVE_PATH")
    private String localSavePath;
    /**
    * 远程基础路径
    */
    @Excel(name = "远程基础路径")
    @TableField(value="FS_BASE_PATH")
    private String fsBasePath;
    /**
    * 远程日期分类路径
    */
    @Excel(name = "远程日期分类路径")
    @TableField(value="FS_DATE_PATH")
    private String fsDatePath;
    /**
    * 远程文件名称
    */
    @Excel(name = "远程文件名称")
    @TableField(value="FS_FILE_NAME")
    private String fsFileName;
    /**
    * 上传人员
    */
    @Excel(name = "上传人员")
    @TableField(value="UPLOAD_USER_ACCOUNT")
    private String uploadUserAccount;
    /**
    * 上传时间
    */
    @Excel(name = "上传时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="UPLOAD_DATE")
    private Date uploadDate;
    /**
    * 删除标志
    */
    @Excel(name = "删除标志")
    @TableField(value="DEL_FLAG")
    private String delFlag;
    @Override
    public Serializable pkVal() {
        return this.battchId;
    }

    @Override
    public void preInsert() {
        Sequence sequence = new Sequence(0, 0);
        this.battchId = String.valueOf(sequence.nextId());
    }
    @Override
    public void preUpdate() {

    }
    @JSONField(serialize = false)
    @Override
    public boolean getIsNewRecord() {
        return isNewRecord || StringUtils.isBlank(getBattchId());
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}


