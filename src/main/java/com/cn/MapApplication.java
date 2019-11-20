package com.cn;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.cn.datasources.DynamicDataSourceConfig;
import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author dgzhangheng
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
@EnableMethodCache(basePackages = "com.cn.modules")
@EnableCreateCacheAnnotation
@MapperScan("com.cn.modules.**.dao")
public class MapApplication extends SpringBootServletInitializer {
	protected final static Logger logger = LoggerFactory.getLogger(MapApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MapApplication.class, args);
		logger.info("Application is success!");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MapApplication.class);
	}
	/**
	 *  json 的设置
	 * @return ConfigurableServletWebServerFactory
	 */
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				connector.setProperty("relaxedQueryChars", "|{}[]");
			}
		});
		return factory;
	}
}
