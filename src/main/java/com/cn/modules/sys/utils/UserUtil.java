package com.cn.modules.sys.utils;

import com.cn.modules.sys.entity.SystemUserEntity;
import org.apache.shiro.SecurityUtils;

public class UserUtil {
    public static SystemUserEntity getUser(){
        return (SystemUserEntity) SecurityUtils.getSubject().getPrincipal();
    }
}
