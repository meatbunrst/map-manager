package com.cn.modules.sys.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangheng
 * @date 2018-10-26  11:53:00
 */
public class TreeVo  implements Serializable {

    private String id;
    private String label;
    private List<TreeVo> children;
    private String icon;
    private boolean disabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public TreeVo(String id, String label,  String icon, boolean disabled) {
        this.id = id;
        this.label = label;
        this.icon = icon;
        this.disabled = disabled;
    }
}
