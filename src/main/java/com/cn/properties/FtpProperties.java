package com.cn.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目相关配置
 *
 * @author fengshuonan
 * @date 2017年10月23日16:44:15
 */
@Configuration
@ConfigurationProperties(prefix = FtpProperties.FTP_PREFIX)
public class FtpProperties {

    public static final String FTP_PREFIX = "ftp";

    private String windowTmp = "D:/tmp";

    private String linuxTmp = "/tmp";

    private String host = "127.0.0.1";

    private Integer port = 21;

    private String user = "zhangheng";

    private String password = "oracle";

    private String ftpBasepath = "/";

    public String getFtpBasepath() {
        return ftpBasepath;
    }

    public void setFtpBasepath(String ftpBasepath) {
        this.ftpBasepath = ftpBasepath;
    }

    public String getWindowTmp() {
        return windowTmp;
    }

    public void setWindowTmp(String windowTmp) {
        this.windowTmp = windowTmp;
    }

    public String getLinuxTmp() {
        return linuxTmp;
    }

    public void setLinuxTmp(String linuxTmp) {
        this.linuxTmp = linuxTmp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
