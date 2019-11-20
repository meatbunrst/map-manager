package com.cn.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2018/1/2.
 * @email 154040976@qq.com
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DruidConfig {


    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("resetEnable", "false");
        initParameters.put("allow", "");
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

  @Bean
  public DruidStatInterceptor getDruidStatInterceptor(){
    return new DruidStatInterceptor();
  }

  @Bean
  @Scope("prototype")
  public JdkRegexpMethodPointcut getJdkRegexpMethodPointcut(){
    JdkRegexpMethodPointcut pointcut=new JdkRegexpMethodPointcut();
    String[] str={"com.cn.**.service.*","com.cn.**.dao.*"};
    pointcut.setPatterns(str);
    return pointcut;
  }

}
