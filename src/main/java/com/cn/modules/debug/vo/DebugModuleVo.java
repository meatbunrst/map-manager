package com.cn.modules.debug.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
/**
* 模板表 entity 对象实体类
* @author zhangheng
* @date 2019-05-25 23:23:02
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DebugModuleVo implements Serializable {
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


