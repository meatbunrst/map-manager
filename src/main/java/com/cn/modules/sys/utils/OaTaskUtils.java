package com.cn.modules.sys.utils;


import com.cn.common.utils.SpringContextHolder;
import com.cn.modules.sys.entity.SysTaskEntity;
import com.cn.modules.sys.service.SysTaskService;

import java.util.List;

/**
 * @author zhangheng
 * @date 2018-06-05  14:27:00
 */
public class OaTaskUtils {

    private static SysTaskService oaTaskService = SpringContextHolder.getBean(SysTaskService.class);



    public static boolean saveUndoTask(String id,String title,long userId, int type){
        SysTaskEntity oaTask = new SysTaskEntity();
        oaTask.setStatus(0);
        oaTask.setProjectId(id);
        oaTask.setTitle(title);
        oaTask.setUserId(userId);
        oaTask.setType(type);
        return oaTaskService.save(oaTask);
    }
    public static boolean updateStatusByProjectId(String id){
        SysTaskEntity entity = new SysTaskEntity();
        entity.setProjectId(id);

        List<SysTaskEntity> oaTasks = oaTaskService.selectList(entity);
        oaTasks.forEach(task -> {
            task.setStatus(1);
        });
        return oaTaskService.updateBatchById(oaTasks);
    }

    /**
     * 保存代办
     * @param task
     * @return
     */
/*    public static boolean saveUndoTask(SysTaskEntity task){
        if (StringUtils.isBlank(task.getStatus())){
            task.setStatus("0");
        }
        return oaTaskService.saveOrUpdate(task);

    }*/

/*

    public static boolean saveUndoTask(String url,String id,String projectName,String userId, String type){

        SysTaskEntity oaTask = new SysTaskEntity();
        oaTask.setStatus("0");
        oaTask.setCheckUrl(url);
        oaTask.setProjectId(id);
        oaTask.setProjectName(projectName);
        oaTask.setUserId(userId);
        oaTask.setType(type);
        return oaTaskService.save(oaTask);
    }

    public static boolean saveUndoRoleTask(String url,String id,String projectName,Long roleId, String type){

        SysTaskEntity oaTask = new SysTaskEntity();
        oaTask.setStatus("0");
        oaTask.setCheckUrl(url);
        oaTask.setProjectId(id);
        oaTask.setProjectName(projectName);
        oaTask.setRoleid(roleId);
        oaTask.setType(type);
        return oaTaskService.save(oaTask);
    }


    public static boolean saveUndodeptTask(String url,String id,String projectName,String deptId, String type){

        SysTaskEntity oaTask = new SysTaskEntity();
        oaTask.setStatus("0");
        oaTask.setCheckUrl(url);
        oaTask.setProjectId(id);
        oaTask.setProjectName(projectName);
        oaTask.setDeptId(deptId);
        oaTask.setType(type);
        return oaTaskService.save(oaTask);
    }

    public static boolean saveUndoTask(String url,String id,String projectName,List<String> userId, String type){
        List<SysTaskEntity> oaTasks = Lists.newArrayList();
        for (String uid :userId){
            SysTaskEntity oaTask = new SysTaskEntity();
            oaTask.setStatus("0");

            oaTask.setCheckUrl(url);
            oaTask.setProjectId(id);
            oaTask.setProjectName(projectName);
            oaTask.setUserId(uid);
            oaTask.setType(type);
            oaTasks.add(oaTask);
        }
        return oaTaskService.save(oaTasks);
    }


    public static boolean updateStatus(int id){
        SysTaskEntity entity = new SysTaskEntity();
        entity.setProjectId(id);

        List<SysTaskEntity> oaTasks = oaTaskService.selectList(entity);
        oaTasks.forEach(task -> {
            task.setStatus(1);
        });
        return oaTaskService.save(oaTasks);
    }

    public static boolean updateStatus(String id,String userId){

        SysTaskEntity entity = new SysTaskEntity();
        entity.setProjectId(id);
        entity.setUserId(userId);
        List<SysTaskEntity> oaTasks = oaTaskService.selectList(entity);
        oaTasks.forEach(task -> {
            task.setStatus("1");
        });
        return oaTaskService.save(oaTasks);
    }

    public static boolean updateStatus(String id,String userId,String url){

        SysTaskEntity entity = new SysTaskEntity();
        entity.setProjectId(id);
        //entity.setUserId(userId);
        List<SysTaskEntity> oaTasks = oaTaskService.selectList(entity);
        oaTasks.forEach(task -> {
            task.setStatus("1");
            task.setCheckUrl(url);
            oaTaskService.updateById(task);
        });
        return true;
    }
*/




}
