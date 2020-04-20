package com.cn.modules.sys.service;


import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.cn.common.utils.RedisUtils;
import com.cn.common.utils.JwtUtils;
import com.cn.common.utils.Result;
import com.cn.modules.sys.entity.SystemUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 系统用户TokenService  业务接口
*
* @author jupiter
* @date 2019-09-05 16:18:31
*/
@Service
@Slf4j
public class SysUserTokenService {



    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    public Result createToken(long userId) {
        Sequence sequence = new Sequence(0, 0);
        //生成一个token

        String tokenId = String.valueOf(userId)+"_"+sequence.nextId();
        String token = jwtUtils.generateToken(tokenId);

        redisUtils.set(tokenId,token,jwtUtils.getExpire());
        Result r = Result.ok().put("token", token).put("expire", jwtUtils.getExpire());
        return r;
    }

    public void logout(SystemUserEntity user) {
        redisUtils.delete(user.getTokenId());
    }
}