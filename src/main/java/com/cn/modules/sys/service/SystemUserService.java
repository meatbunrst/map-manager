package com.cn.modules.sys.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.common.service.AbstractService;
import com.cn.common.utils.Constant;
import com.cn.common.utils.RedisUtils;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.dao.SysMenuDao;
import com.cn.modules.sys.dao.SystemUserDao;
import com.cn.modules.sys.entity.SysMenuEntity;
import com.cn.modules.sys.entity.SystemUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
* 用户信息表Service  业务接口
*
* @author zhangheng
* @date 2019-04-30 14:43:36
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
@Slf4j
public class SystemUserService extends AbstractService<SystemUserDao,SystemUserEntity> {


    @Autowired
    private SysMenuDao sysMenuDao;



    @CacheEvict(value="Menu",allEntries = true)
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean updateById(SystemUserEntity entity) {
        return this.retBool(this.baseMapper.updateById(entity));
    }

    @Override
    @CacheEvict(value="Menu",allEntries = true)
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean updateBatchById(Collection entityList) {
        return this.updateBatchById(entityList, 1000);
    }

    @CacheEvict(value="Menu",allEntries = true)
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean save(SystemUserEntity entity) {
        return this.retBool(this.baseMapper.insert(entity));
    }

    @Override
    @CacheEvict(value="Menu",allEntries = true)
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean saveBatch(Collection entityList) {
        return this.saveBatch(entityList, 1000);
    }

    @CacheEvict(value="Menu",allEntries = true)
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean removeByIds( List<Long> idList) {
        return SqlHelper.delBool(this.baseMapper.deleteBatchIds(idList));
    }

    @Transactional(rollbackFor={RuntimeException.class})
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SystemUserEntity userEntity = new SystemUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SystemUserEntity>().eq("user_id", userId).eq("password", password));
    }
    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SystemUserEntity model){
        return dao.selectCountByModel(model);
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SystemUserEntity model){
        return SqlHelper.delBool(dao.deleteByModel(model));
    }
    /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param model 实体对象封装操作类（可以为 null）
    * @return List<SystemUserEntity>
    */
    public List<SystemUserEntity> selectList(SystemUserEntity model){
        return list(getWrapper(model));
    }
    /**
     * 根据 model 条件，生成LambdaQueryWrapper
     *
     * @param model 实体对象
     * @return LambdaQueryWrapper
     */
    public LambdaQueryWrapper<SystemUserEntity> getWrapper(SystemUserEntity model){
        LambdaQueryWrapper<SystemUserEntity> wrapper = new LambdaQueryWrapper<>();
        if (model != null){
            if (ToolUtil.isNotEmpty(model.getUserId())){
                wrapper.eq(SystemUserEntity::getUserId,model.getUserId());
            }
            if (ToolUtil.isNotEmpty(model.getUsername())){
                wrapper.like(SystemUserEntity::getUsername,model.getUsername());
            }
            if (ToolUtil.isNotEmpty(model.getPassword())){
                wrapper.eq(SystemUserEntity::getPassword,model.getPassword());
            }
            if (ToolUtil.isNotEmpty(model.getSalt())){
                wrapper.eq(SystemUserEntity::getSalt,model.getSalt());
            }
            if (ToolUtil.isNotEmpty(model.getEmail())){
                wrapper.eq(SystemUserEntity::getEmail,model.getEmail());
            }
            if (ToolUtil.isNotEmpty(model.getMobile())){
                wrapper.eq(SystemUserEntity::getMobile,model.getMobile());
            }
            if (ToolUtil.isNotEmpty(model.getStatus())){
                wrapper.eq(SystemUserEntity::getStatus,model.getStatus());
            }
            if (ToolUtil.isNotEmpty(model.getCreateUserId())){
                wrapper.eq(SystemUserEntity::getCreateUserId,model.getCreateUserId());
            }
            if (ToolUtil.isNotEmpty(model.getCreateTime())){
                wrapper.eq(SystemUserEntity::getCreateTime,model.getCreateTime());
            }
            if (ToolUtil.isNotEmpty(model.getDeptId())){
                wrapper.eq(SystemUserEntity::getDeptId,model.getDeptId());
            }
            if (ToolUtil.isNotEmpty(model.getAccountName())){
                wrapper.like(SystemUserEntity::getAccountName,model.getAccountName());
            }
            if (ToolUtil.isNotEmpty(model.getWechatId())){
                wrapper.eq(SystemUserEntity::getWechatId,model.getWechatId());
            }
            if (ToolUtil.isNotEmpty(model.getLastLoginTime())){
                wrapper.eq(SystemUserEntity::getLastLoginTime,model.getLastLoginTime());
            }
            if (ToolUtil.isNotEmpty(model.getGender())){
                wrapper.eq(SystemUserEntity::getGender,model.getGender());
            }
            if (ToolUtil.isNotEmpty(model.getAvatarurl())){
                wrapper.eq(SystemUserEntity::getAvatarurl,model.getAvatarurl());
            }
        }
        return wrapper;
    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param pagination 分页查询条件
     * @param model   SQL包装
     * @return List<SystemUserEntity>
     */
    public List<SystemUserEntity> selectPage(Page pagination, SystemUserEntity model){
        return dao.queryPage(pagination,getWrapper(model));
    }

    /**
    * <p>
    * 根据 entity 条件，查询全部记录（并翻页）
    * </p>
    *
    * @param pagination 分页查询条件
    * @param model   实体对象封装操作可以为 null）
    * @param wrapper   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SystemUserEntity> selectPage(Page pagination, SystemUserEntity model, Wrapper<SystemUserEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);

    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SystemUserEntity>
    */
    public List<SystemUserEntity> selectPage(Page pagination, Wrapper<SystemUserEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

    @Cached(name="userPermissionsCache-", key="#userId", expire = 3600)
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = dao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    public List<Long> queryAllMenuId(Long userId) {
        return dao.queryAllMenuId(userId);
    }

    @Cached(name="userCache-", key="#userId", expire = 3600)
    public SystemUserEntity queryUser(Long userId) {
        SystemUserEntity entity = dao.selectById(userId);
        return entity;
    }
}
