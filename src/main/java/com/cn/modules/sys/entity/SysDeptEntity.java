package com.cn.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.cn.common.entity.AbstractModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
/**
* 部门表 entity 对象实体类
*
* @author zhangheng
* @date 2018-10-26 10:45:50
*/
@TableName("SYS_DEPT")
public class SysDeptEntity extends AbstractModel<SysDeptEntity> {
    private static final long serialVersionUID = 1L;
    /**
    * 主键ID
    */
    @Excel(name = "主键ID")
    @TableId(value ="ID",type = IdType.ID_WORKER_STR)
    private String id;
    /**
    * 排序
    */
    @Excel(name = "排序")
    @TableField(value="NUM")
    private Long num;
    /**
    * 父部门id
    */
    @Excel(name = "父部门id")
    @TableField(value="PID")
    private String pid;
    /**
    * 父级ids
    */
    @Excel(name = "父级ids")
    @TableField(value="PIDS")
    private String pids;
    /**
    * 简称
    */
    @Excel(name = "简称")
    @TableField(value="SIMPLENAME")
    private String simplename;
    /**
    * 全称
    */
    @Excel(name = "全称")
    @TableField(value="FULLNAME")
    private String fullname;
    /**
    * 提示

    */
    @Excel(name = "提示 ")
    @TableField(value="TIPS")
    private String tips;
    /**
    * 版本（乐观锁保留字段）
    */
    @Excel(name = "版本（乐观锁保留字段）")
    @TableField(value="VERSION")
    private Long version;
    /**
    * 地址
    */
    @Excel(name = "地址")
    @TableField(value="ADRESS")
    private String adress;
    /**
    * 电话
    */
    @Excel(name = "电话")
    @TableField(value="PHONE")
    private String phone;
    /**
    * 公司名
    */
    @Excel(name = "公司名")
    @TableField(value="COMPANY")
    private String company;

    @TableField(exist = false)
    private String pName;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
    * 获取: 主键ID
    */
    public String getId() {
        return id;
    }
    /**
    * 设置: 主键ID
    * 
    */
    public void setId(String id) {
        this.id = id;
    }
    /**
    * 获取: 排序
    */
    public Long getNum() {
        return num;
    }
    /**
    * 设置: 排序
    * 
    */
    public void setNum(Long num) {
        this.num = num;
    }
    /**
    * 获取: 父部门id
    */
    public String getPid() {
        return pid;
    }
    /**
    * 设置: 父部门id
    * 
    */
    public void setPid(String pid) {
        this.pid = pid;
    }
    /**
    * 获取: 父级ids
    */
    public String getPids() {
        return pids;
    }
    /**
    * 设置: 父级ids
    * 
    */
    public void setPids(String pids) {
        this.pids = pids;
    }
    /**
    * 获取: 简称
    */
    public String getSimplename() {
        return simplename;
    }
    /**
    * 设置: 简称
    * 
    */
    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }
    /**
    * 获取: 全称
    */
    public String getFullname() {
        return fullname;
    }
    /**
    * 设置: 全称
    * 
    */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    /**
    * 获取: 提示

    */
    public String getTips() {
        return tips;
    }
    /**
    * 设置: 提示

    * 
    */
    public void setTips(String tips) {
        this.tips = tips;
    }
    /**
    * 获取: 版本（乐观锁保留字段）
    */
    public Long getVersion() {
        return version;
    }
    /**
    * 设置: 版本（乐观锁保留字段）
    * 
    */
    public void setVersion(Long version) {
        this.version = version;
    }
    /**
    * 获取: 地址
    */
    public String getAdress() {
        return adress;
    }
    /**
    * 设置: 地址
    * 
    */
    public void setAdress(String adress) {
        this.adress = adress;
    }
    /**
    * 获取: 电话
    */
    public String getPhone() {
        return phone;
    }
    /**
    * 设置: 电话
    * 
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
    * 获取: 公司名
    */
    public String getCompany() {
        return company;
    }
    /**
    * 设置: 公司名
    * 
    */
    public void setCompany(String company) {
        this.company = company;
    }

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


