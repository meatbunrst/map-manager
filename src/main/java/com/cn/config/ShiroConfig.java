package com.cn.config;

import com.cn.modules.sys.oauth2.OAuth2Filter;
import com.cn.modules.sys.oauth2.OAuth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-20 18:33
 */
@Configuration
public class ShiroConfig {


 @Bean("securityManager")
 public SecurityManager securityManager(OAuth2Realm oAuth2Realm) {
     DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
     securityManager.setRealm(oAuth2Realm);
     securityManager.setRememberMeManager(null);
     return securityManager;
 }
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
//        filters.put("user", new CustomUserFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/app/**", "anon");
        filterMap.put("/sys/login", "anon");
//      工作流的设置
        filterMap.put("/static/**", "anon");
        filterMap.put("/bpmn/**", "anon");
        filterMap.put("/act/**", "anon");
        filterMap.put("/autoload-cache/**", "anon");
        filterMap.put("/service/**", "anon");
        filterMap.put("/process-definition/**", "anon");
//      接口的设置
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/batchattach/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/getCode", "anon");

        filterMap.put("/web/**","anon");


        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
