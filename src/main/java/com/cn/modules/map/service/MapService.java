package com.cn.modules.map.service;


import com.alibaba.fastjson.JSON;
import com.cn.common.utils.RedisUtils;
import com.cn.common.utils.Result;
import com.cn.common.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MapZoneInfoService mapZoneInfoService;

    /**
     *清除地图缓存数据
     * @param key
     */
    public void redisRemove(String key){
        if (ToolUtil.isEmpty(key)){
            redisUtils.deleteKeys("tian_map_");
        }else{
            redisUtils.deleteKeys("tian_map_"+key);
        }

    }

    /**
     * 东莞地图数据
     * @param tree 层级
     * @return
     */
    public Object getDgMap(Integer tree){
        Result result=new Result();
        //从缓存中拿数据
        String mapStr=null;
        if (redisUtils.exists("tian_map_getDgMap"+tree)){
            mapStr=redisUtils.get("tian_map_getDgMap"+tree);
        }
        if (mapStr==null){
            //缓存数据无效 再次查询
            result.put("data",mapZoneInfoService.getDgMap(tree));
            log.info("东莞地图 查询取数");
        }else{
            result.put("data", JSON.parse(mapStr));
            log.info("东莞地图 缓存取数");
        }
        return result;
    }

    public Object getDgDateDown(String name){
        Result result=new Result();
        //从缓存中拿数据
        String mapStr=null;
        if (redisUtils.exists("tian_map_getDgDateDown"+name)){
            mapStr=redisUtils.get("tian_map_getDgDateDown"+name);
        }
        if (mapStr==null){
            //缓存数据无效 再次查询
            result.put("data",mapZoneInfoService.getDgDateDown(name));
            log.info("东莞地图下钻数据 查询取数");
        }else{
            result.put("data", JSON.parse(mapStr));
            log.info("东莞地图下钻数据 缓存取数");
        }
        return result;
    }

    public Object getDgList(Integer tree){
        Result result=new Result();
        //从缓存中拿数据
        String mapStr=null;
        if (redisUtils.exists("tian_map_getDgList"+tree)){
            mapStr=redisUtils.get("tian_map_getDgList"+tree);
        }
        if (mapStr==null){
            //缓存数据无效 再次查询
            result.put("data",mapZoneInfoService.getDgList(tree));
            log.info("东莞地图list数据 查询取数");
        }else{
            result.put("data", JSON.parse(mapStr));
            log.info("东莞地图list数据 缓存取数");
        }
        return result;
    }

}
