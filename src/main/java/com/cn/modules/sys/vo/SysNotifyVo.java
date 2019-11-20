package com.cn.modules.sys.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* 通知通告 entity 对象实体类
* @author zhangheng
* @date 2019-05-08 09:34:05
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysNotifyVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 编号
    */
    private String id;
    /**
    * 类型
    */
    private String type;
    /**
    * 标题
    */
    private String title;
    /**
    * 内容
    */
    private String content;
    /**
    * 附件
    */
    private String files;
    /**
    * 状态
    */
    private String status;
    /**
    * 创建者
    */
    private String createBy;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /**
    * 更新者
    */
    private String updateBy;
    /**
    * 更新时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    /**
    * 备注信息
    */
    private String remarks;
    /**
    * 删除标记
    */
    private String delFlag;
    /**
    * 备胎
    */
    private String mark;
    /**
    * AttachIds
    */
    private String attachIds;
    /**
    * 通知的用户信息
    */
    private String infoIds;
    /**
    * 用户类型
    */
    private String userType;
    /**
    * 通知总数
    */
    private Long sumNumber;

}


