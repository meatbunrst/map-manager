package com.cn.modules.map.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.utils.R;
import com.cn.common.utils.ShiroUtils;
import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.form.SysLoginForm;
import com.cn.modules.sys.service.SysUserTokenService;
import com.cn.modules.sys.service.SystemUserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author tianqian
 * @date 2019/11/8 10:01
 */
@RestController
@RequestMapping("/web")
public class MapLoginController {
    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * map网页端登录
     */
    @PostMapping("/login")
    public Map<String, Object> login(SysLoginForm form)throws IOException {
        if (!StrUtil.hasEmpty(form.getApollo(),form.getApp())){
            form.setApollo(ShiroUtils.getDecrypt(form.getApollo(),form.getUuid()));
            form.setApp(ShiroUtils.getDecrypt(form.getApp(),form.getUuid()));
        }else {
            return R.error("账号或密码不正确");
        }
        LambdaQueryWrapper<SystemUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUserEntity::getUsername,form.getApollo());
        //用户信息
        SystemUserEntity user = (SystemUserEntity) systemUserService.getOne(wrapper);

        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(new Sha256Hash(form.getApp(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0){
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(user.getUserId()).put("user",user);
        return r;
    }

}
