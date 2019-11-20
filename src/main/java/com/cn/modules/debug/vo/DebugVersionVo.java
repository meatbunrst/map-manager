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
* 版本迭代 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:31:11
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DebugVersionVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 编号
    */
    private Integer id;
    /**
    * 迭代名称
    */
    private String name;
    /**
    * 迭代代号
    */
    private String code;
    /**
    * 开始日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
    * 结束日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
    * 可用工作日
    */
    private Integer sumWorkday;
    /**
    * 迭代类型
    */
    private Integer status;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /**
    * 创建人
    */
    private String createBy;
    /**
    * 关联项目ID
    */
    private Integer projectId;
    /**
    * 描述
    */
    private String describe;

}


