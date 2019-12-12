package com.cn.common.utils;

import cn.hutool.core.util.StrUtil;
import com.cn.common.exception.RRException;
import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.service.SystemUserService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangheng
 * @date 2019-09-03  09:41:00
 */
@Component
public class TokenUtil {


    public static SystemUserEntity getUser(){
        RedisUtils redisUtils = (RedisUtils) SpringContextUtils.getBean("redisUtils");
        JwtUtils jwtUtils = (JwtUtils) SpringContextUtils.getBean("jwtUtils");
        SystemUserService systemUserService = (SystemUserService) SpringContextUtils.getBean("systemUserService");

        Claims claims = getClaims();
        if(claims == null ){
            throw new RRException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        if (!redisUtils.exists(claims.getSubject())){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        } else {
            redisUtils.get(claims.getSubject(),jwtUtils.getExpire());
        }
        //  设置token 登出的时候使用

        Long userRealId = Long.parseLong(StrUtil.subBefore(claims.getSubject(),"_",false));
        SystemUserEntity user = systemUserService.queryUser(userRealId);
        user.setTokenId(claims.getSubject());

        return user;
    }

    public static boolean verify(){
        Claims claims = getClaims();
        if(claims == null ){
            return false;
        }else {
            return true;
        }
    }

    public static Claims getClaims(){

        JwtUtils jwtUtils = (JwtUtils) SpringContextUtils.getBean("jwtUtils");
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        String token = request.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        if (StrUtil.isNotEmpty(token) && token.startsWith("Bearer ")){
            token = token.substring(7);
        } else {
            token = null;
        }

       return jwtUtils.getClaimByToken(token);
    }

}
