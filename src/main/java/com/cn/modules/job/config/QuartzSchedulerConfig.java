package com.cn.modules.job.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zhangheng
 * @date 2018-08-01  20:51:00
 */
@Configuration
public class QuartzSchedulerConfig {



    private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerConfig.class);
    private static final String QUARTZ_PROPERTIES_NAME = "/quartz.properties";


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        try {
            factoryBean.setQuartzProperties(quartzProperties());
            factoryBean.setDataSource(dataSource);

            factoryBean.setSchedulerName("earthScheduler");
            // 延时启动
            factoryBean.setStartupDelay(1);
            factoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
            // 可选，QuartzScheduler
            factoryBean.setOverwriteExistingJobs(true);
            // 设置自动启动，默认为true
            factoryBean.setAutoStartup(false);
        } catch (Exception e) {
            logger.error("加载 {} 配置文件失败.", QUARTZ_PROPERTIES_NAME, e);
            throw new RuntimeException("加载配置文件失败", e);
        }
        return factoryBean;
    }



    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_NAME));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }



}
