package com.cn.debug;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.cn.modules.sys.entity.SysNotifyEntity;
import com.cn.modules.sys.service.SysNotifyService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
* 通知通告Test  测试业务接口
*
* @author zhangheng
* @date 2019-05-08 09:34:04
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysNotifyTest {

    @Autowired
    private SysNotifyService sysNotifyService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysNotifyEntity entity = new SysNotifyEntity();
        int count = sysNotifyService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysNotifyEntity entity = new SysNotifyEntity();
        List<SysNotifyEntity> list1 =sysNotifyService.selectList(entity);

        QueryWrapper<SysNotifyEntity> queryWrapper = new QueryWrapper<>();
        List<SysNotifyEntity> list2 = sysNotifyService.list(queryWrapper);

        log.info("查询完成");
    }




    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysNotifyEntity entity = new SysNotifyEntity();
        boolean execute = sysNotifyService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysNotifyEntity entity = new SysNotifyEntity();
        // 这个填写地址
        boolean execute = sysNotifyService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysNotifyEntity entity = new SysNotifyEntity();
        boolean execute = sysNotifyService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysNotifyEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysNotifyEntity entity = new SysNotifyEntity();
            entity.setId(String.valueOf(sequence.nextId()));

            entity.setTitle(sequence.nextId()+"：title");
            entity.setContent(sequence.nextId()+"：Content");
            entity.setCreateDate(new Date());
            list.add(entity);
        }
        boolean execute = sysNotifyService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
