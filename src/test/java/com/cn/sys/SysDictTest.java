package com.cn.sys;

import com.cn.modules.sys.entity.SysDictEntity;
import com.cn.modules.sys.service.SysDictService;
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
* 字典表Test  测试业务接口
*
* @author zhangheng
* @date 2019-08-30 17:37:39
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysDictTest {

    @Autowired
    private SysDictService sysDictService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysDictEntity entity = new SysDictEntity();
        int count = sysDictService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysDictEntity entity = new SysDictEntity();
        List<SysDictEntity> list1 = sysDictService.selectList(entity);

        QueryWrapper<SysDictEntity> queryWrapper = new QueryWrapper<>();
        List<SysDictEntity> list2 = sysDictService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SysDictEntity> wrapper = new LambdaQueryWrapper<>();
        SysDictEntity entity = new SysDictEntity();

        wrapper.eq(SysDictEntity::getId,entity.getId());
        wrapper.eq(SysDictEntity::getDictName,entity.getDictName());
        wrapper.eq(SysDictEntity::getRemark,entity.getRemark());
        List<SysDictEntity> list = sysDictService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysDictEntity entity = new SysDictEntity();
        boolean execute = sysDictService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysDictEntity entity = new SysDictEntity();
        // 这个填写地址
        boolean execute = sysDictService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysDictEntity entity = new SysDictEntity();
        boolean execute = sysDictService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysDictEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysDictEntity entity = new SysDictEntity();
            entity.setId(String.valueOf(sequence.nextId()));
            list.add(entity);
        }
        boolean execute = sysDictService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
