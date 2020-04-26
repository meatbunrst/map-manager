package com.cn.modules.map.service;

import com.alibaba.fastjson.JSON;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.RedisUtils;
import com.cn.modules.map.dao.MapZoneInfoDao;
import com.cn.modules.map.entity.MapZoneInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 区域信息描边表Service  业务接口
*
* @author tianqian
* @date 2020-04-16 16:42:52
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class MapZoneInfoService extends AbstractService<MapZoneInfoDao,MapZoneInfoEntity> {
    @Autowired
    private RedisUtils redisUtils;

    /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param model 实体对象封装操作类（可以为 null）
    * @return List<MapZoneInfoEntity>
    */
    public List<MapZoneInfoEntity> selectList(MapZoneInfoEntity model){
        return dao.selectListModel(model);
    }

    /**
     * 得到东莞得区域信息geo.json
     * @return
     */
    public Object getDgMap(Integer tree){
        MapZoneInfoEntity model=new MapZoneInfoEntity();
        //查询层级
        model.setZoneTree(tree);
        List<MapZoneInfoEntity> list=this.selectList(model);
        Map<String,Object> result=this.getGeoJson(list);
        Map<String,Object> json=new HashMap<>();
        json.put("geoJson",result);
        //json.put("centreLon","");
       // json.put("centreLat","");
      //  json.put("outline","");
        //永久缓存
        redisUtils.set("tian_map_getDgMap"+tree, JSON.toJSON(json),RedisUtils.NOT_EXPIRE);
        return json;
    }


    /**
     * 得到东莞的下钻数据
     * @param name
     * @return
     */
    public Object getDgDateDown(String name){
        MapZoneInfoEntity model=new MapZoneInfoEntity();
        model.setZoneName(name);
        //得到此条数据对象
        MapZoneInfoEntity entity=this.selectOne(model);

        MapZoneInfoEntity params=new MapZoneInfoEntity();
        params.setPid(entity.getZoneId());
        List<MapZoneInfoEntity> list=this.selectList(params);
        Map<String,Object> result=this.getGeoJson(list);

        Map<String,Object> json=new HashMap<>();
        json.put("geoJson",result);
        json.put("centreLon",entity.getLon());
        json.put("centreLat",entity.getLat());
        json.put("outline",entity.getPath());
        redisUtils.set("tian_map_getDgDateDown"+name, JSON.toJSON(json),RedisUtils.NOT_EXPIRE);
        return json;
    }

    /**
     * 得到地图的GeoJson
     * @param list
     * @return
     */
    public  Map<String,Object> getGeoJson( List<MapZoneInfoEntity> list){
        List<Object> features=new ArrayList<>();
        for(MapZoneInfoEntity obj:list){
            Map<String,Object> feature=new HashMap();
            feature.put("type","Feature");
            Map<String,Object> properties=new HashMap<>();
            properties.put("name",obj.getZoneName());
            feature.put("properties", properties);
            Map<String,Object> geometry=new HashMap<>();
            geometry.put("type","MultiPolygon");
            List<Object> coordinates=new ArrayList<>();
            List<Object> coordinates_1=new ArrayList<>();
            List<Object> coordinates_2=new ArrayList<>();
            String pathObj=obj.getPath();
            String[] paths=pathObj.split("\\|");
            for (String path:paths){
                String[] k=path.split(",");
                double[]  kk=k.length>0?new double[]{Double.parseDouble(k[0]),Double.parseDouble(k[1])}:null;
                coordinates_2.add(kk);
            }
            coordinates_1.add(coordinates_2);
            coordinates.add(coordinates_1);
            geometry.put("coordinates",coordinates);
            feature.put("geometry", geometry);
            features.add(feature);
        }

        Map<String,Object> result=new HashMap<>();
        result.put("type","FeatureCollection");
        result.put("features",features);
        return result;
    }

    public Object getDgList(Integer tree){
        MapZoneInfoEntity model=new MapZoneInfoEntity();
        //查询层级
        model.setZoneTree(tree);
        List<MapZoneInfoEntity> list=this.selectList(model);
        //永久缓存
        redisUtils.set("tian_map_getDgList"+tree, JSON.toJSON(list),RedisUtils.NOT_EXPIRE);
        return list;
    }

}
