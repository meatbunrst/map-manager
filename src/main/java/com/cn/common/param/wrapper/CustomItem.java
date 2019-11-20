//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cn.common.param.wrapper;


import cn.hutool.json.JSONUtil;
import com.cn.common.param.enums.EFindType;
import com.cn.common.param.enums.EJoinType;
import com.cn.common.param.enums.EQDType;

/**
 * @author dgzhangheng
 */
public class CustomItem {
    private String name;
    private EJoinType joinType;
    private EFindType findType;
    private EQDType dataType;
    private String[] values;
    private String description;
    private CustomItem[] conditions;

    public CustomItem() {
        this.dataType = EQDType.Default;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EJoinType getJoinType() {
        return this.joinType;
    }

    public void setJoinType(EJoinType joinType) {
        this.joinType = joinType;
    }

    public EFindType getFindType() {
        return this.findType;
    }

    public void setFindType(EFindType findType) {
        this.findType = findType;
    }

    public String[] getValues() {
        return this.values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public CustomItem[] getConditions() {
        return this.conditions;
    }

    public void setConditions(CustomItem[] conditions) {
        this.conditions = conditions;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "CustomItem {name=" + this.name + ", joinType=" + this.joinType + ", findType=" + this.findType + ", values=" + JSONUtil.toJsonStr(this.values) + '}';
    }

    public EQDType getDataType() {
        return this.dataType;
    }

    public void setDataType(EQDType dataType) {
        this.dataType = dataType;
    }
}
