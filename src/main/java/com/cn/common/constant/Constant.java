package com.cn.common.constant;

/**
 * 一些服务的快捷获取
 *
 * @author zhangheng
 * @date 2017-03-30 15:58
 */
public class Constant {

    public static final String INIT_PASSWORD = "123@456";
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    public enum DropStatus {

        /**
         * before 位置
         */
        BEFORE("before"),
        AFTER("after"),
        INNER("inner");

        private String value;

        private DropStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
