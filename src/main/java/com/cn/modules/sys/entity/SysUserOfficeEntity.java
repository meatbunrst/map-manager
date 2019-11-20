package com.cn.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.common.entity.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
/**
* 用户部门 entity 对象实体类
* @author zhangheng
* @date 2019-05-08 14:17:30
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("SYS_USER_OFFICE")
public class SysUserOfficeEntity extends AbstractModel<SysUserOfficeEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * UserId
    */
    @Excel(name = "UserId")
    @TableId(value ="USER_ID",type = IdType.ID_WORKER_STR)
    private String userId;
    /**
    * OfficeId
    */
    @Excel(name = "OfficeId")
    @TableId(value ="OFFICE_ID",type = IdType.ID_WORKER_STR)
    private String officeId;
    @Override
    public Serializable pkVal() {
        return this.officeId;
    }

    @Override
    public void preInsert() {

    }
    @Override
    public void preUpdate() {

    }
    @JSONField(serialize = false)
    @Override
    public boolean getIsNewRecord() {
        return false;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}


