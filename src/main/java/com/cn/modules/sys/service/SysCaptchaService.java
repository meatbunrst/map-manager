package com.cn.modules.sys.service;


import java.awt.image.BufferedImage;

import cn.hutool.core.util.StrUtil;
import com.cn.common.exception.RRException;
import com.cn.common.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 系统验证码Service  业务接口
*
* @author jupiter
* @date 2019-09-05 15:57:37
*/
@Service
@Slf4j
public class SysCaptchaService {

    @Autowired
    private Producer producer;

    /**
     * 5分钟后过期
     */
    public final static int EXPIRE = 300;

    @Autowired
    private RedisUtils redisUtils;

    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        System.err.println("验证码："+code);
        redisUtils.set(uuid,code,EXPIRE);
        return producer.createImage(code);
    }


    public boolean validate(String uuid, String code) {

        boolean flag = false;
        if (StrUtil.isNotEmpty(uuid) && redisUtils.exists(uuid) && redisUtils.get(uuid).equalsIgnoreCase(code)){
            redisUtils.delete(uuid);
            flag = true;
        }
        return flag;
    }


    public String getCode(String uuid) {
        if (StrUtil.isNotEmpty(uuid)){
            return redisUtils.get(uuid);
        }else {
            return null;
        }
    }

}