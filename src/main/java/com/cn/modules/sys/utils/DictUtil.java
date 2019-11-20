package com.cn.modules.sys.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.common.utils.SpringContextHolder;
import com.cn.modules.sys.entity.SysDictDetailedEntity;
import com.cn.modules.sys.entity.SysDictEntity;
import com.cn.modules.sys.service.SysDictDetailedService;
import com.cn.modules.sys.service.SysDictService;

import java.util.List;

/**
 * @author dgzhangheng
 */
public class DictUtil {


    private static SysDictService sysDictService = SpringContextHolder.getBean(SysDictService.class);

    private static SysDictDetailedService sysDictDetailedService = SpringContextHolder.getBean(SysDictDetailedService.class);

    public static List<SysDictDetailedEntity> getEntities(String name){

        QueryWrapper<SysDictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_name",name);
        SysDictEntity entity = (SysDictEntity) sysDictService.getOne(queryWrapper);

        if (entity != null){
            SysDictDetailedEntity dictDetailedEntity = new SysDictDetailedEntity();
            dictDetailedEntity.setLabelId(entity.getId());
            List<SysDictDetailedEntity> list = sysDictDetailedService.selectList(dictDetailedEntity);
            return list;
        }else {
            return null;
        }
    }


    public static String getLabel(String name,String value){

        QueryWrapper<SysDictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_name",name);
        SysDictEntity entity = (SysDictEntity) sysDictService.getOne(queryWrapper);

        String key = null;
        if (entity != null){
            SysDictDetailedEntity dictDetailedEntity = new SysDictDetailedEntity();
            dictDetailedEntity.setLabelId(entity.getId());
            List<SysDictDetailedEntity> list = sysDictDetailedService.selectList(dictDetailedEntity);

            for (SysDictDetailedEntity sysDictDetailedEntity : list) {
                if (StrUtil.isNotEmpty(sysDictDetailedEntity.getParamValue()) && sysDictDetailedEntity.getParamValue().trim().equals(value.trim())){
                    key = sysDictDetailedEntity.getParamKey();
                    break;
                }
            }
        }
        return key;
    }
}
