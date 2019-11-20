package com.cn.modules.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
* 部门表 entity 对象实体类
* @author zhangheng
* @date 2019-05-09 20:35:23
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysDeptVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String draggingNodeId;
    /**
     * 名称
     */
    private String dropNodeId;

    private String dropType;

}


