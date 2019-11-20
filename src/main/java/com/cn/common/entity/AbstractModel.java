package com.cn.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;


/**
 * @author zhangheng
 * @date 2018-08-02  05:57:00
 */
public abstract class AbstractModel<T> extends Model {
    private static final long serialVersionUID = 1L;

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    @TableField(exist = false)
    @JSONField(serialize = false)
    protected boolean isNewRecord = false;

    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert();


    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate();

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     * @return
     */
    public abstract boolean getIsNewRecord();
}
