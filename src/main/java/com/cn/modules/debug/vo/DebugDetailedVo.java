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
* bug详情 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:36:55
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DebugDetailedVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 主键ID
    */
    private Integer id;
    /**
    * 标题
    */
    private String title;
    /**
    * 模板ID
    */
    private Integer moduleId;
    /**
    * 项目ID
    */
    private Integer projectId;
    /**
    * 指派用户
    */
    private Integer userId;
    /**
    * 版本id
    */
    private Integer versionId;
    /**
    * Type
    */
    private Integer type;
    /**
    * 优先级
    */
    private Integer priority;
    /**
    * 严重程度
    */
    private Integer level;
    /**
    * 关键词
    */
    private String keyWork;
    /**
    * 截止日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
    * 内容
    */
    private String content;
    /**
    * 附件ID
    */
    private String fileIds;
    /**
    * 创建人
    */
    private String createBy;
    /**
    * 创建日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

}


