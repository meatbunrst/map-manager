//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cn.common.param.enums;

/**
 * @author dgzhangheng
 */

public enum EJoinType {
    And("1", "与"),
    Or("2", "或");

    private String val;
    private String msg;

    private EJoinType(String value, String msg) {
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
