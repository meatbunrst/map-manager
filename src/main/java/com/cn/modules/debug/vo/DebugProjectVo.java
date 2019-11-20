package com.cn.modules.debug.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
* 项目表 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:11:10
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DebugProjectVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 主键id
    */
    private Integer id;
    /**
    * 项目名
    */
    private String name;
    /**
    * 状态
    */
    private Integer status;
    /**
    * bug总数
    */
    private Integer sumBug;
    /**
    * 描述
    */
    private String marks;
    /**
    * 文件ID字符串
    */
    private String fileIds;
    /**
    * 起始时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /**
    * 结束时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
    * 项目拥有人
    */
    private String ownPerson;

}


