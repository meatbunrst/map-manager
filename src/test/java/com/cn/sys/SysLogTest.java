package com.cn.sys;

import com.cn.modules.sys.entity.SysLogEntity;
import com.cn.modules.sys.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import java.util.List;
import com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
* 系统日志Test  测试业务接口
*
* @author zhangheng
* @date 2019-09-19 22:15:00
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysLogTest {

    @Autowired
    private SysLogService sysLogService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysLogEntity entity = new SysLogEntity();
        int count = sysLogService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysLogEntity entity = new SysLogEntity();
        List<SysLogEntity> list1 = sysLogService.selectList(entity);

        QueryWrapper<SysLogEntity> queryWrapper = new QueryWrapper<>();
        List<SysLogEntity> list2 = sysLogService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SysLogEntity> wrapper = new LambdaQueryWrapper<>();
        SysLogEntity entity = new SysLogEntity();

        wrapper.eq(SysLogEntity::getId,entity.getId());
        wrapper.eq(SysLogEntity::getUsername,entity.getUsername());
        wrapper.eq(SysLogEntity::getOperation,entity.getOperation());
        wrapper.eq(SysLogEntity::getMethod,entity.getMethod());
        wrapper.eq(SysLogEntity::getParams,entity.getParams());
        wrapper.eq(SysLogEntity::getTime,entity.getTime());
        wrapper.eq(SysLogEntity::getIp,entity.getIp());
        wrapper.eq(SysLogEntity::getCreateDate,entity.getCreateDate());
        List<SysLogEntity> list = sysLogService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysLogEntity entity = new SysLogEntity();
        boolean execute = sysLogService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysLogEntity entity = new SysLogEntity();
        // 这个填写地址
        boolean execute = sysLogService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysLogEntity entity = new SysLogEntity();
        boolean execute = sysLogService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysLogEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysLogEntity entity = new SysLogEntity();
            entity.setId(sequence.nextId());
            list.add(entity);
        }
        boolean execute = sysLogService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
