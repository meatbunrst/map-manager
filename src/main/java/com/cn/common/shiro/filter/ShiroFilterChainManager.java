package com.cn.common.shiro.filter;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.data.redis.core.StringRedisTemplate;

/* *
 * @Author tomsun28
 * @Description Filter 管理器
 * @Date 11:16 2018/2/28
 */
@Component
public class ShiroFilterChainManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroFilterChainManager.class);

//    private final StringRedisTemplate redisTemplate;

//    @Autowired
//    private GunsProperties gunsProperties;


    // 初始化获取过滤链
    public Map<String, Filter> initGetFilters() {
        Map<String, Filter> filters = new LinkedHashMap<>();
//        filters.put("authc", new FormAuthenticationFilter());
        filters.put("user", new CustomUserFilter());

        return filters;
    }
    // 初始化获取过滤链规则
    public Map<String,String> initGetFilterChain() {
        Map<String,String> filterChain = new LinkedHashMap<>();
        // -------------anon 默认过滤器忽略的URL
//        List<String> defalutAnon = Arrays.asList(gunsProperties.getShiroAnon().split("\\s*,\\s*"));

        List<String> defalutAnon = Arrays.asList("".split("\\s*,\\s*"));
        defalutAnon.forEach(ignored -> filterChain.put(ignored,"anon"));
        // -------------auth 默认需要认证过滤器的URL 走auth--authc
        List<String> defalutAuth = Arrays.asList("/login");
        defalutAuth.forEach(auth -> filterChain.put(auth,"authc"));
        List<String> userFilter = Arrays.asList("/**");
        userFilter.forEach(auth -> filterChain.put(auth,"user"));
        return filterChain;
    }

}
