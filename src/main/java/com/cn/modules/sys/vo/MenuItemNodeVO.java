package com.cn.modules.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
* 菜单条件 entity 对象实体类
* @author zhangheng
* @date 2019-04-12 11:51:29
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuItemNodeVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private Long draggingNodeId;
    /**
    * 名称
    */
    private Long dropNodeId;

    private String dropType;


}


