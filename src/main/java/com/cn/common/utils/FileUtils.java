package com.cn.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangheng
 * @date 2018-11-08  09:50:00
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 两个目录拼接
     * @param dirA
     * @param dirB
     * @return
     */
    public static String joinDirectory(String dirA, String dirB) {
        if(dirB != null && dirB.length() > 0) {
            char c = dirB.charAt(0);
            String dirB2 = dirB;
            if(c == '\\' || c == '/') {
                dirB2 = dirB.substring(1, dirB.length());
            }
            return perfectDirectory(dirA)+dirB2;
        } else {
            return dirA;
        }
    }
    public static String joinDirectory(String... dirs) {
        String result = dirs[0];
        for(int i = 1; i < dirs.length; ++i) {
            result = joinDirectory(result, dirs[i]);
        }
        return result;
    }
    /*------------------------------------------------------------------------------------------------
     *
     * 这个是之前的架构里面的 ，所以要慢慢的去掉，而且在Linux的命令行下是有问题的。
     * */
    /**
     * 加一个/   方便拼接
     * @param dir
     * @return
     */
    public static String perfectDirectory(String dir) {
        String fileSeparator = "/";
        if (StringUtils.isNotBlank(dir)){
            dir = dir.trim();
            char c = dir.charAt(dir.length() - 1);

            if(c != '\\' && c != '/') {
                dir = dir + fileSeparator;
            }
        }else {
            dir="";
        }
        return dir;
    }
}
