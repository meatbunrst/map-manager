/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com). <p> Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at <p> http://www.apache.org/licenses/LICENSE-2.0
 * <p> Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cn.common.aspect;


import com.cn.common.annotation.Permission;
import com.cn.common.utils.TokenUtil;
import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * AOP 权限自定义检查
 * @author zhangheng
 */
@Aspect
@Component
@Slf4j
public class PermissionAop {

    @Autowired
    private SystemUserService shiroService;

    @Pointcut(value = "@annotation(com.cn.common.annotation.Permission)")
    private void cutPermission() {
    }
    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        boolean hasAnyPermission = false;
        String[] permissions = permission.value();

        SystemUserEntity userEntity = TokenUtil.getUser();
        Set<String> permissionSet = shiroService.getUserPermissions(userEntity.getUserId());
        for (String s : permissions) {
            if (permissionSet.contains(s)){
                hasAnyPermission = true;
                break;
            }
        }

        if (hasAnyPermission) {
            return point.proceed();
        } else {
            log.error("权限的设置为",permissions);
            throw new UnauthorizedException("没有权限:"+  Arrays.toString(permissions) +"，请联系管理员");
        }

    }

}
