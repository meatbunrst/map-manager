package com.cn.modules.sys.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.R;
import com.cn.modules.sys.entity.SysLogEntity;
import com.cn.modules.sys.service.RedisService;
import com.cn.modules.sys.vo.RedisVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
* 日志控制器
*
* @author zhangheng
* @date 2019-04-28 13:47:05
*/
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController extends AbstractController {

    @Autowired
    private RedisService redisService;

    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        List<CustomItem> listItem = JSONUtil.parseArray(json).toList(CustomItem.class);
        Page<RedisVo> page = new PageFactory<RedisVo>().defaultPage();
//        List<RedisVo> list = redisService.findByKey("*");
        List<RedisVo> list = Lists.newArrayList();
        if (!listItem.isEmpty()){
            list =  redisService.findByKey(listItem.get(0).getValues()[0]);
        }else {
            list = redisService.findByKey("*");
        }
        int fromIndex = (int) ((page.getCurrent() - 1 )* page.getSize());
        int toIndex = (int) (page.getCurrent() * page.getSize() );

        page.setTotal(list.size());
        if(fromIndex > list.size()){
            return new ArrayList();
        } else if(toIndex >= list.size()) {
            list = list.subList(fromIndex,list.size());
        } else {
            return list.subList(fromIndex,toIndex);
        }
        page.setRecords(list);
        return R.ok().put("page", new PageUtils(page));
    }


    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    public Object add(@RequestBody SysLogEntity entity) {
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("syslog:delete")
    public Object delete(@RequestBody String[] ids) {
        for (String id : ids) {
            redisService.delete(id);
        }
        return R.ok();
    }

    /**
    * 清空所有数据
    * @return
    */
    @PostMapping(value = "/flushdb")
    public Object flushdb() {
        redisService.flushdb();
        return R.ok();
    }

}