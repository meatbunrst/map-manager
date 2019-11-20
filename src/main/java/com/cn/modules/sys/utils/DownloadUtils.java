package com.cn.modules.sys.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.cn.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author zhangheng
 * @date 2018-07-30  17:05:00
 */
public class DownloadUtils {

    /**
     * 安全目录，不允许访问
     */
    public static final String[] NOTACCESS_PATH = {"/etc/"};

    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String downloadFileName, String fullFilePath) {

        if (StringUtils.isNotBlank(fullFilePath)){
            fullFilePath = FileUtil.normalize(fullFilePath);
        }else {
            fullFilePath = AttachUtils.getUploadPath();
        }
        System.out.println("fullpath="+fullFilePath);
        //简单安全检查，防止进入系统目录
        for(int i = 0; i < NOTACCESS_PATH.length; i++) {
            if(fullFilePath.startsWith(NOTACCESS_PATH[i])) {
                throw  new RRException("文件不存在");
            }
        }
        File file = new File(fullFilePath);
        if (!FileUtil.exist(file)){
            throw  new RRException("文件不存在");
        }
        try {
            response.setHeader("name",URLEncoder.encode(downloadFileName,"UTF-8"));
            if (isIE(request)) {
                downloadFileName = URLEncoder.encode(downloadFileName, "UTF8");
            } else {
                downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-disposition", "attachment;filename="+downloadFileName);
        try {
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            String mimeType = mimeTypesMap.getContentType(downloadFileName);

            if (mimeType == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            response.setContentType(mimeType);
            response.setContentLength((int) FileUtil.size(file));

            FileInputStream inputStream =  new FileInputStream(fullFilePath);
            OutputStream os = response.getOutputStream();
            IoUtil.copy(inputStream,os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void downloadFileByString(HttpServletRequest request, HttpServletResponse response, String downloadFileName, String content) {

        File file = FileUtil.writeUtf8String(content,FileUtil.getTmpDirPath()+"/"+downloadFileName);
        try {
            response.setHeader("name",URLEncoder.encode(downloadFileName,"UTF-8"));

            if (isIE(request)) {
                downloadFileName = URLEncoder.encode(downloadFileName, "UTF8");
            } else {
                downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-disposition", "attachment;filename="+downloadFileName);
        try {
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            String mimeType = mimeTypesMap.getContentType(downloadFileName);

            if (mimeType == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            response.setContentType(mimeType);

            FileInputStream inputStream =  new FileInputStream(file);
            response.setContentLength((int) FileUtil.size(file));
            OutputStream os = response.getOutputStream();
            IoUtil.copy(inputStream,os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否为IE浏览器
     * @param request
     * @return
     */
    public static boolean isIE(HttpServletRequest request) {
        return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0 || request.getHeader("USER-AGENT").toLowerCase().indexOf("edge") > 0;
    }

}
