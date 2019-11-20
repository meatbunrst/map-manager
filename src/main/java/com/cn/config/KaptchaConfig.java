package com.cn.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**
 * 生成验证码配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-20 19:22
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.session.key", "code");
        properties.put("kaptcha.textproducer.char.length", "3");
        properties.put("kaptcha.image.width", "100");
        properties.put("kaptcha.image.height", "36");

        properties.put("kaptcha.textproducer.font.size", "30");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
