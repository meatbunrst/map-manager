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
* 角色用户 entity 对象实体类
* @author zhangheng
* @date 2019-05-14 17:37:21
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("SYS_USER_ROLE")
public class SysUserRoleEntity extends AbstractModel<SysUserRoleEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * Id
    */
    @Excel(name = "Id")
    @TableId(value ="ID",type = IdType.ID_WORKER)
    private Long id;
    /**
    * UserId
    */
    @Excel(name = "UserId")
    @TableField(value="USER_ID")
    private Long userId;
    /**
    * RoleId
    */
    @Excel(name = "RoleId")
    @TableField(value="ROLE_ID")
    private Long roleId;
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


