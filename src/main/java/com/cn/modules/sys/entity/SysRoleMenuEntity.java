package com.cn.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import com.cn.common.entity.AbstractModel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
/**
* 角色与菜单对应关系 entity 对象实体类
* @author zhangheng
* @date 2019-09-06 15:20:10
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
public class SysRoleMenuEntity extends AbstractModel<SysRoleMenuEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * Id
    */
    @Excel(name = "Id")
    @TableId(value ="id",type = IdType.ID_WORKER)
    private Long id;
    /**
    * 角色ID
    */
    @Excel(name = "角色ID")
    @TableField(value="role_id")
    private Long roleId;
    /**
    * 菜单ID
    */
    @Excel(name = "菜单ID")
    @TableField(value="menu_id")
    private Long menuId;
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


