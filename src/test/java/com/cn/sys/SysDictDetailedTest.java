package com.cn.sys;

import com.cn.modules.sys.entity.SysDictDetailedEntity;
import com.cn.modules.sys.service.SysDictDetailedService;
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
* 系统配置信息表Test  测试业务接口
*
* @author zhangheng
* @date 2019-08-30 17:40:18
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysDictDetailedTest {

    @Autowired
    private SysDictDetailedService sysDictDetailedService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysDictDetailedEntity entity = new SysDictDetailedEntity();
        int count = sysDictDetailedService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysDictDetailedEntity entity = new SysDictDetailedEntity();
        List<SysDictDetailedEntity> list1 = sysDictDetailedService.selectList(entity);

        QueryWrapper<SysDictDetailedEntity> queryWrapper = new QueryWrapper<>();
        List<SysDictDetailedEntity> list2 = sysDictDetailedService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SysDictDetailedEntity> wrapper = new LambdaQueryWrapper<>();
        SysDictDetailedEntity entity = new SysDictDetailedEntity();

        wrapper.eq(SysDictDetailedEntity::getId,entity.getId());
        wrapper.eq(SysDictDetailedEntity::getParamKey,entity.getParamKey());
        wrapper.eq(SysDictDetailedEntity::getParamValue,entity.getParamValue());
        wrapper.eq(SysDictDetailedEntity::getStatus,entity.getStatus());
        wrapper.eq(SysDictDetailedEntity::getRemark,entity.getRemark());
        wrapper.eq(SysDictDetailedEntity::getLabel,entity.getLabel());
        wrapper.eq(SysDictDetailedEntity::getLabelId,entity.getLabelId());
        List<SysDictDetailedEntity> list = sysDictDetailedService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysDictDetailedEntity entity = new SysDictDetailedEntity();
        boolean execute = sysDictDetailedService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysDictDetailedEntity entity = new SysDictDetailedEntity();
        // 这个填写地址
        boolean execute = sysDictDetailedService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysDictDetailedEntity entity = new SysDictDetailedEntity();
        boolean execute = sysDictDetailedService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysDictDetailedEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysDictDetailedEntity entity = new SysDictDetailedEntity();
//            entity.setId(sequence.nextId());
            list.add(entity);
        }
        boolean execute = sysDictDetailedService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
