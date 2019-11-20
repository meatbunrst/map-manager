package com.cn.sys;

import com.cn.modules.sys.entity.SysMenuGroupEntity;
import com.cn.modules.sys.service.SysMenuGroupService;
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
* 代码生成组Test  测试业务接口
*
* @author zhangheng
* @date 2019-09-18 21:17:38
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysMenuGroupTest {

    @Autowired
    private SysMenuGroupService sysMenuGroupService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysMenuGroupEntity entity = new SysMenuGroupEntity();
        int count = sysMenuGroupService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysMenuGroupEntity entity = new SysMenuGroupEntity();
        List<SysMenuGroupEntity> list1 = sysMenuGroupService.selectList(entity);

        QueryWrapper<SysMenuGroupEntity> queryWrapper = new QueryWrapper<>();
        List<SysMenuGroupEntity> list2 = sysMenuGroupService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SysMenuGroupEntity> wrapper = new LambdaQueryWrapper<>();
        SysMenuGroupEntity entity = new SysMenuGroupEntity();

        wrapper.eq(SysMenuGroupEntity::getId,entity.getId());
        wrapper.eq(SysMenuGroupEntity::getName,entity.getName());
        wrapper.eq(SysMenuGroupEntity::getCode,entity.getCode());
        wrapper.eq(SysMenuGroupEntity::getCreateTime,entity.getCreateTime());
        wrapper.eq(SysMenuGroupEntity::getUpdateTime,entity.getUpdateTime());
        wrapper.eq(SysMenuGroupEntity::getRemarks,entity.getRemarks());
        List<SysMenuGroupEntity> list = sysMenuGroupService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysMenuGroupEntity entity = new SysMenuGroupEntity();
        boolean execute = sysMenuGroupService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysMenuGroupEntity entity = new SysMenuGroupEntity();
        // 这个填写地址
        boolean execute = sysMenuGroupService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysMenuGroupEntity entity = new SysMenuGroupEntity();
        boolean execute = sysMenuGroupService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysMenuGroupEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysMenuGroupEntity entity = new SysMenuGroupEntity();
            entity.setId(String.valueOf(sequence.nextId()));
            list.add(entity);
        }
        boolean execute = sysMenuGroupService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
