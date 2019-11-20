package com.cn.common.param.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangheng
 * @date 2019-03-06  22:43:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {
    private String name;
    private String type;
    private String joinType;

}
