package com.cn.common.annotation;

import java.lang.annotation.*;

/**
 * 权限注解 用于检查权限 规定访问权限
 *
 * @example @Permission({1,2,3})
 * @example @Permission
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {
    String[] value() default {};
}
