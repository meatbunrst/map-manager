package com.cn.debug;

import com.cn.modules.sys.entity.SysTaskEntity;
import com.cn.modules.sys.service.SysTaskService;
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
* 任务通知表Test  测试业务接口
*
* @author zhangheng
* @date 2019-08-09 15:29:50
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysTaskTest {

    @Autowired
    private SysTaskService sysTaskService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysTaskEntity entity = new SysTaskEntity();
        int count = sysTaskService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysTaskEntity entity = new SysTaskEntity();
        List<SysTaskEntity> list1 = sysTaskService.selectList(entity);

        QueryWrapper<SysTaskEntity> queryWrapper = new QueryWrapper<>();
        List<SysTaskEntity> list2 = sysTaskService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SysTaskEntity> wrapper = new LambdaQueryWrapper<>();
        SysTaskEntity entity = new SysTaskEntity();

        wrapper.eq(SysTaskEntity::getId,entity.getId());
        wrapper.eq(SysTaskEntity::getTitle,entity.getTitle());
        wrapper.eq(SysTaskEntity::getContent,entity.getContent());
        wrapper.eq(SysTaskEntity::getUserId,entity.getUserId());
        wrapper.eq(SysTaskEntity::getType,entity.getType());
        wrapper.eq(SysTaskEntity::getStatus,entity.getStatus());
        wrapper.eq(SysTaskEntity::getProjectId,entity.getProjectId());
        wrapper.eq(SysTaskEntity::getCreateDate,entity.getCreateDate());
        wrapper.eq(SysTaskEntity::getCreateBy,entity.getCreateBy());
        List<SysTaskEntity> list = sysTaskService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysTaskEntity entity = new SysTaskEntity();
        boolean execute = sysTaskService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysTaskEntity entity = new SysTaskEntity();
        // 这个填写地址
        boolean execute = sysTaskService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysTaskEntity entity = new SysTaskEntity();
        entity.setTitle("342432");
        boolean execute = sysTaskService.save(entity);

        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysTaskEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysTaskEntity entity = new SysTaskEntity();
            entity.setId(String.valueOf(sequence.nextId()));

            entity.setCreateDate(new Date());
            entity.setTitle(sequence.nextId()+"标题");
            list.add(entity);
        }
        boolean execute = sysTaskService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
