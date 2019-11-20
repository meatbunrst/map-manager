//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cn.common.param.enums;

/**
 * @author dgzhangheng
 */

public enum EFindType {
    EQ("1", "等于"),
    NOT_EQ("2", "不等于"),
    GT("3", "大于"),
    GTE("4", "大于等于"),
    LT("5", "小于"),
    LTE("6", "小于等于"),
    LIKE("7", "匹配"),
    NOT_LIKE("8", "不匹配"),
    BETWEEN("9", "范围"),
    IN("10", "包含"),
    NOT_IN("11", "不包含"),
    IS_NULL("12", "为空"),
    IS_NOT_NULL("13", "不为空");

    private String val;
    private String msg;

    private EFindType(String value, String msg) {
        this.val = value;
        this.msg = msg;
    }

    public String val() {
        return this.val;
    }

    public String msg() {
        return this.msg;
    }
}
