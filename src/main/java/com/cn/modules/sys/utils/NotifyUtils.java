package com.cn.modules.sys.utils;

import com.cn.common.utils.SpringContextHolder;
import com.cn.modules.sys.entity.SysNotifyEntity;
import com.cn.modules.sys.service.SysNotifyService;

import java.util.Date;

/**
 * @author zhangheng
 * @date 2019-08-09  16:10:00
 */
public class NotifyUtils {


    private static SysNotifyService notifyService = SpringContextHolder.getBean(SysNotifyService.class);


    public static boolean saveNotify(String id,String projectName, String type){
        SysNotifyEntity oaTask = new SysNotifyEntity();
        oaTask.setTitle(projectName);

        oaTask.setCreateDate(new Date());
        oaTask.setType(type);
        return notifyService.save(oaTask);
    }


    public static boolean saveNotify(String id,String projectName, String content,String type){
        SysNotifyEntity oaTask = new SysNotifyEntity();
        oaTask.setTitle(projectName);

        oaTask.setContent(content);
        oaTask.setCreateDate(new Date());
        oaTask.setType(type);
        return notifyService.save(oaTask);
    }


}
