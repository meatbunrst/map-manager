package com.cn.modules.map.controller;

import com.cn.common.utils.Result;
import com.cn.modules.map.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/map")
public class MapController {
    @Autowired
    private MapService mapService;

    @GetMapping("/getDgDate")
    public Object getDgDate(Integer tree){
        String errMsg="";
        try{
            return mapService.getDgMap(tree);
        }catch (Exception e ){
            errMsg=e.toString();
        }
        return Result.error(errMsg);
    }


}
