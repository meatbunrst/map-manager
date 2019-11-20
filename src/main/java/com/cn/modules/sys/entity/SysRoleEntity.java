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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
* 角色 entity 对象实体类
* @author zhangheng
* @date 2019-08-30 17:28:20
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
public class SysRoleEntity extends AbstractModel<SysRoleEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * RoleId
    */
    @Excel(name = "RoleId")
    @TableId(value ="role_id",type = IdType.AUTO)
    private Long roleId;
    /**
    * 角色名称
    */
    @Excel(name = "角色名称")
    @TableField(value="role_name")
    private String roleName;
    /**
    * 备注
    */
    @Excel(name = "备注")
    @TableField(value="remark")
    private String remark;
    /**
    * 创建者ID
    */
    @Excel(name = "创建者ID")
    @TableField(value="create_user_id")
    private Long createUserId;
    /**
    * 创建时间
    */
    @Excel(name = "创建时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="create_time")
    private Date createTime;

    @TableField(exist=false)
    private List<Long> menuIdList;
    @Override
    public Serializable pkVal() {
        return this.roleId;
    }

    @Override
    public void preInsert() {
        Sequence sequence = new Sequence(0, 0);
//        this.roleId = Ssequence.nextId();
    }
    @Override
    public void preUpdate() {

    }
    @JSONField(serialize = false)
    @Override
    public boolean getIsNewRecord() {
        return isNewRecord || getRoleId() == null;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}


