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
* 用户信息表 entity 对象实体类
* @author zhangheng
* @date 2019-04-30 14:43:36
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("SYS_USER")
public class SystemUserEntity extends AbstractModel<SystemUserEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @Excel(name = "主键")
    @TableId(value ="USER_ID",type = IdType.ID_WORKER)
    private Long userId;
    /**
    * 用户名
    */
    @Excel(name = "用户名")
    @TableField(value="USERNAME")
    private String username;
    /**
    * 密码
    */
    @Excel(name = "密码")
    @JSONField(serialize = false)
    @TableField(value="PASSWORD")
    private String password;
    /**
    * Salt
    */
    @Excel(name = "Salt")
    @TableField(value="SALT")
    private String salt;
    /**
    * 邮箱
    */
    @Excel(name = "邮箱")
    @TableField(value="EMAIL")
    private String email;
    /**
    * 电话
    */
    @Excel(name = "电话")
    @TableField(value="MOBILE")
    private String mobile;
    /**
    * 状态
    */
    @Excel(name = "状态")
    @TableField(value="STATUS")
    private Long status;
    /**
    * 创建用户名
    */
    @Excel(name = "创建用户名")
    @TableField(value="CREATE_USER_ID")
    private Long createUserId;
    /**
    * 创建时间
    */
    @Excel(name = "创建时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="CREATE_TIME")
    private Date createTime;
    /**
    * 部门ID
    */
    @Excel(name = "部门ID")
    @TableField(value="DEPT_ID")
    private String deptId;
    /**
    * 账户名
    */
    @Excel(name = "账户名")
    @TableField(value="ACCOUNT_NAME")
    private String accountName;

    /**
     * 微信id
     */
    @Excel(name = "微信id")
    @TableField(value="wechat_id")
    private String wechatId;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value="last_login_time")
    private Date lastLoginTime;
    /**
     * 性别
     */
    @Excel(name = "性别")
    @TableField(value="gender")
    private Integer gender;
    /**
     * 照片地址
     */
    @Excel(name = "照片地址")
    @TableField(value="avatarUrl")
    private String avatarurl;
    /**
     * 城市
     */
    @Excel(name = "城市")
    @TableField(value="city")
    private String city;

    @TableField(exist=false)
    private List<Long> roleIdList;

    @TableField(exist = false)
    private String deptName;


    @TableField(exist = false)
    private String createName;


    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private String tokenId;

    @Override
    public Serializable pkVal() {
        return this.userId;
    }

    @Override
    public void preInsert() {
        Sequence sequence = new Sequence(0, 0);
        this.userId = sequence.nextId();
    }
    @Override
    public void preUpdate() {

    }
    @JSONField(serialize = false)
    @Override
    public boolean getIsNewRecord() {
        return isNewRecord || getUserId() == null;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}


