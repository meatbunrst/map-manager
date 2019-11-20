package com.cn.common.constant.state;

/**
 * 数据库排序
 *
 * @author zhangheng
 * @Date 2017年5月31日20:48:41
 */
public enum Order {

    ASC("ascending"), DESC("descending");

    private String des;

    Order(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
