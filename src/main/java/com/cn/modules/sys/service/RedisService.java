package com.cn.modules.sys.service;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.utils.RedisUtils;
import com.cn.modules.sys.vo.RedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author zhangheng
 * @date  2019-09-19 11:14 PM
 */
@Service
public class RedisService {


    @Autowired
    JedisPool pool;

    public List findByKey(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            List<RedisVo> redisVos = new ArrayList<>();

            if(!key.equals("*")){
                key = "*" + key + "*";
            }
            for (String s : jedis.keys(key)) {
                RedisVo redisVo = new RedisVo(s,jedis.get(s));
                redisVos.add(redisVo);
            }

            return redisVos;
        }finally{
            if(null != jedis){
                jedis.close(); // 释放资源还给连接池
            }
        }

    }

    public void save(RedisVo redisVo) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.set(redisVo.getKey(),redisVo.getValue());
        }finally{
            if(null != jedis){
                jedis.close(); // 释放资源还给连接池
            }
        }
    }

    public void delete(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.del(key);
        }finally{
            if(null != jedis){
                jedis.close(); // 释放资源还给连接池
            }
        }

    }

    public void flushdb() {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.flushDB();
        }finally{
            if(null != jedis){
                jedis.close(); // 释放资源还给连接池
            }
        }

    }

}
