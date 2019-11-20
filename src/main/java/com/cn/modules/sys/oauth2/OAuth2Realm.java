package com.cn.modules.sys.oauth2;

import cn.hutool.core.util.StrUtil;
import com.cn.common.exception.RRException;
import com.cn.common.utils.RedisUtils;
import com.cn.modules.app.utils.JwtUtils;
import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.service.SystemUserService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-20 14:00
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SystemUserEntity user = (SystemUserEntity)principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = systemUserService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        //根据accessToken，查询用户信息

        //查询用户信息

        Claims claims = jwtUtils.getClaimByToken(accessToken);

        if(claims == null ){
            throw new RRException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        if (!redisUtils.exists(claims.getSubject())){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        } else {
            redisUtils.get(claims.getSubject(),jwtUtils.getExpire());
        }
        Long userRealId = Long.parseLong(StrUtil.subBefore(claims.getSubject(),"_",false));

        SystemUserEntity entity = systemUserService.queryUser(userRealId);

        entity.setTokenId(claims.getSubject());

        //账号锁定
        if(entity.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(entity, accessToken, getName());
        return info;
    }
}
