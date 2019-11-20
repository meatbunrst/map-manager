package com.cn.sys;

import com.cn.modules.sys.entity.SysMenuItemEntity;
import com.cn.modules.sys.service.SysMenuItemService;
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
* 菜单条件Test  测试业务接口
*
* @author zhangheng
* @date 2019-09-18 21:17:28
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysMenuItemTest {

    @Autowired
    private SysMenuItemService sysMenuItemService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysMenuItemEntity entity = new SysMenuItemEntity();
        int count = sysMenuItemService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysMenuItemEntity entity = new SysMenuItemEntity();
        List<SysMenuItemEntity> list1 = sysMenuItemService.selectList(entity);

        QueryWrapper<SysMenuItemEntity> queryWrapper = new QueryWrapper<>();
        List<SysMenuItemEntity> list2 = sysMenuItemService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SysMenuItemEntity> wrapper = new LambdaQueryWrapper<>();
        SysMenuItemEntity entity = new SysMenuItemEntity();

        wrapper.eq(SysMenuItemEntity::getId,entity.getId());
        wrapper.eq(SysMenuItemEntity::getName,entity.getName());
        wrapper.eq(SysMenuItemEntity::getMenuSort,entity.getMenuSort());
        wrapper.eq(SysMenuItemEntity::getSysId,entity.getSysId());
        wrapper.eq(SysMenuItemEntity::getPermission,entity.getPermission());
        wrapper.eq(SysMenuItemEntity::getMenuType,entity.getMenuType());
        wrapper.eq(SysMenuItemEntity::getMeenuHref,entity.getMeenuHref());
        wrapper.eq(SysMenuItemEntity::getRemarks,entity.getRemarks());
        wrapper.eq(SysMenuItemEntity::getCreateTime,entity.getCreateTime());
        wrapper.eq(SysMenuItemEntity::getUpdateTime,entity.getUpdateTime());
        List<SysMenuItemEntity> list = sysMenuItemService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysMenuItemEntity entity = new SysMenuItemEntity();
        boolean execute = sysMenuItemService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysMenuItemEntity entity = new SysMenuItemEntity();
        // 这个填写地址
        boolean execute = sysMenuItemService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysMenuItemEntity entity = new SysMenuItemEntity();
        boolean execute = sysMenuItemService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysMenuItemEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysMenuItemEntity entity = new SysMenuItemEntity();
            entity.setId(String.valueOf(sequence.nextId()));
            list.add(entity);
        }
        boolean execute = sysMenuItemService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
