package com.cn.sys;

import com.cn.modules.sys.entity.SysRoleEntity;
import com.cn.modules.sys.service.SysRoleService;
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
* 角色Test  测试业务接口
*
* @author zhangheng
* @date 2019-08-30 17:28:20
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysRoleTest {

    @Autowired
    private SysRoleService sysRoleService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SysRoleEntity entity = new SysRoleEntity();
        int count = sysRoleService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SysRoleEntity entity = new SysRoleEntity();
        List<SysRoleEntity> list1 = sysRoleService.selectList(entity);

        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
        List<SysRoleEntity> list2 = sysRoleService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SysRoleEntity> wrapper = new LambdaQueryWrapper<>();
        SysRoleEntity entity = new SysRoleEntity();

        wrapper.eq(SysRoleEntity::getRoleId,entity.getRoleId());
        wrapper.eq(SysRoleEntity::getRoleName,entity.getRoleName());
        wrapper.eq(SysRoleEntity::getRemark,entity.getRemark());
        wrapper.eq(SysRoleEntity::getCreateUserId,entity.getCreateUserId());
        wrapper.eq(SysRoleEntity::getCreateTime,entity.getCreateTime());
        List<SysRoleEntity> list = sysRoleService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SysRoleEntity entity = new SysRoleEntity();
        boolean execute = sysRoleService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SysRoleEntity entity = new SysRoleEntity();
        // 这个填写地址
        boolean execute = sysRoleService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SysRoleEntity entity = new SysRoleEntity();
        boolean execute = sysRoleService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SysRoleEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SysRoleEntity entity = new SysRoleEntity();
//            entity.setRoleId(sequence.nextId());
            list.add(entity);
        }
        boolean execute = sysRoleService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
