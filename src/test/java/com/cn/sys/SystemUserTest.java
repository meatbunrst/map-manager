package com.cn.sys;

import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.service.SystemUserService;
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
* 系统用户Test  测试业务接口
*
* @author zhangheng
* @date 2019-09-19 14:49:04
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SystemUserTest {

    @Autowired
    private SystemUserService systemUserService;

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectCountTest(){

        SystemUserEntity entity = new SystemUserEntity();
        int count = systemUserService.selectCount(entity);

        log.info("数量count："+count);
    }

    /**
    * 根据 model 条件，查询总记录数
    */
    @Test
    public void selectAllTest(){

        SystemUserEntity entity = new SystemUserEntity();
        List<SystemUserEntity> list1 = systemUserService.selectList(entity);

        QueryWrapper<SystemUserEntity> queryWrapper = new QueryWrapper<>();
        List<SystemUserEntity> list2 = systemUserService.list(queryWrapper);

        log.info("查询完成");
    }

    /**
    * 根据 LambdaQuery 条件，查询总记录数
    */
    @Test
    public void LambdaQueryTest(){
        LambdaQueryWrapper<SystemUserEntity> wrapper = new LambdaQueryWrapper<>();
        SystemUserEntity entity = new SystemUserEntity();

        wrapper.eq(SystemUserEntity::getUserId,entity.getUserId());
        wrapper.eq(SystemUserEntity::getUsername,entity.getUsername());
        wrapper.eq(SystemUserEntity::getPassword,entity.getPassword());
        wrapper.eq(SystemUserEntity::getSalt,entity.getSalt());
        wrapper.eq(SystemUserEntity::getEmail,entity.getEmail());
        wrapper.eq(SystemUserEntity::getMobile,entity.getMobile());
        wrapper.eq(SystemUserEntity::getStatus,entity.getStatus());
        wrapper.eq(SystemUserEntity::getCreateUserId,entity.getCreateUserId());
        wrapper.eq(SystemUserEntity::getCreateTime,entity.getCreateTime());
        wrapper.eq(SystemUserEntity::getDeptId,entity.getDeptId());
        wrapper.eq(SystemUserEntity::getAccountName,entity.getAccountName());
        wrapper.eq(SystemUserEntity::getWechatId,entity.getWechatId());
        wrapper.eq(SystemUserEntity::getLastLoginTime,entity.getLastLoginTime());
        wrapper.eq(SystemUserEntity::getGender,entity.getGender());
        wrapper.eq(SystemUserEntity::getAvatarurl,entity.getAvatarurl());
        wrapper.eq(SystemUserEntity::getCity,entity.getCity());
        List<SystemUserEntity> list = systemUserService.list(wrapper);
        log.info("查询完成");
    }


    /**
    * 根据 model 条件，删除 测试
    */
    @Test
    public void deleteByModelTest(){
        SystemUserEntity entity = new SystemUserEntity();
        boolean execute = systemUserService.deleteByModel(entity);
        log.info("执行结果："+execute);
    }


    /**
    * 根据 model 条件，更新测试
    */
    @Test
    public void updateByModelTest(){
        SystemUserEntity entity = new SystemUserEntity();
        // 这个填写地址
        boolean execute = systemUserService.updateById(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 entity 测试
    */
    @Test
    public void addTest(){
        SystemUserEntity entity = new SystemUserEntity();
        boolean execute = systemUserService.save(entity);
        log.info("执行结果："+execute);
    }

    /**
    * 添加 批量 测试
    */
    @Test
    public void insertBatchTest(){
        Sequence sequence = new Sequence(0, 0);
        List<SystemUserEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            SystemUserEntity entity = new SystemUserEntity();
            entity.setUserId(sequence.nextId());
            list.add(entity);
        }
        boolean execute = systemUserService.saveBatch(list);
        log.info("执行结果："+execute);
    }

}
