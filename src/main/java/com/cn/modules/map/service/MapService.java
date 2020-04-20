package com.cn.modules.map.service;


import com.alibaba.fastjson.JSON;
import com.cn.common.utils.RedisUtils;
import com.cn.common.utils.Result;
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

}
