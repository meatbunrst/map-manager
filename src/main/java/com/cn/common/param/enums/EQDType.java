//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cn.common.param.enums;

/**
 * @author dgzhangheng
 */

public enum EQDType {
    Default("0", "默认"),
    Date("1", "日期"),
    Time("2", "时间");

    private String val;
    private String msg;

    private EQDType(String value, String msg) {
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
