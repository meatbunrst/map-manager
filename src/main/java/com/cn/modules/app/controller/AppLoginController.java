package com.cn.modules.app.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.druid.sql.visitor.functions.Char;
import com.cn.common.utils.R;
import com.cn.common.validator.ValidatorUtils;
import com.cn.config.WxMaConfiguration;
import com.cn.modules.app.entity.UserInfo;
import com.cn.modules.app.entity.WxLoginInfo;
import com.cn.modules.app.form.LoginForm;
import com.cn.modules.app.form.RegisterForm;
import com.cn.modules.app.utils.JwtUtils;
import com.cn.modules.sys.controller.AbstractController;
import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.service.SysUserTokenService;
import com.cn.modules.sys.service.SystemUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.cn.common.constant.Constant.INIT_PASSWORD;

/**
 * APP登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app/auth")
public class AppLoginController  extends AbstractController {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${wx.miniapp.appid}")
    private String appid;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 微信账号登录
     * @param form
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        //用户登录
        SystemUserEntity user = new SystemUserEntity();
        user.setUsername(form.getUsername());
        //用户信息
        user = systemUserService.selectOne(user);


        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0){
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(user.getUserId());

        if(user==null){
            //账号密码错误
            return R.error("账号或者密码错误");
        }else{
            UserInfo userInfo = new UserInfo();
            userInfo.setNickName(user.getUsername());
            r.put("userInfo", userInfo);
            return R.ok(r);
        }

    }

    /**
     * 微信账号登录
     * @param wxLoginInfo
     * @param request
     * @return
     */
    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return R.error("参数为空");
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SystemUserEntity entity = new SystemUserEntity();
        entity.setWechatId(openId);
        SystemUserEntity userEntity = systemUserService.selectOne(entity);

        if (userEntity != null){
            userEntity.setLastLoginTime(new Date());
            systemUserService.updateById(userEntity);

        } else {
            userEntity =new SystemUserEntity();
            userEntity.setWechatId(openId);
//            systemUserEntity.setCreateUserId(getUserId());
            userEntity.setAccountName(userInfo.getNickName());
            userEntity.setCreateTime(new Date());
            userEntity.setLastLoginTime(new Date());
            String salt = RandomStringUtils.randomAlphanumeric(20);
            userEntity.setSalt(salt);
            userEntity.setPassword( new Sha256Hash(INIT_PASSWORD, salt).toHex());
            //唯一性
            userEntity.setUsername(openId);

            userEntity.setAvatarurl(userInfo.getAvatarUrl());
            userEntity.setCity(userInfo.getCity());
            userEntity.setGender(Integer.valueOf(userInfo.getGender()));
            systemUserService.save(userEntity);
        }
        R r = sysUserTokenService.createToken(userEntity.getUserId());
        return r.put("userInfo",userInfo);

    }


    /**
     * 小程序端账号注册
     * @param form
     * @return
     */
    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        SystemUserEntity systemUserEntity = new SystemUserEntity();
        //校验短信验证码---form.getCode()

        //校验username唯一性
        SystemUserEntity user = new SystemUserEntity();
        user.setUsername(form.getUsername());
        user = systemUserService.selectOne(user);
        if(user!=null){
            return R.error("账号已被注册");
        }
        //一个手机号只能绑定一个账号

        systemUserEntity.setUsername(form.getUsername());
        systemUserEntity.setMobile(form.getMobile());
        systemUserEntity.setCreateTime(new Date());

        String salt = RandomStringUtils.randomAlphanumeric(20);
        systemUserEntity.setSalt(salt);
        systemUserEntity.setPassword( new Sha256Hash(form.getPassword(), salt).toHex());
        systemUserEntity.setLastLoginTime(new Date());

        //微信端临时code   form.getWxCode()

        systemUserService.save(systemUserEntity);

        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(systemUserEntity.getUsername());

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(systemUserEntity.getUserId());
        r.put("userInfo",userInfo);
        return R.ok(r);
    }

    /**
     * 小程序端 密码重置
     * @return
     */
    @PostMapping("reset")
    public Object reset( ) {

        return R.ok();
    }

    /**
     * 小程序端 登出
     * @param userId
     * @return
     */
    @PostMapping("logout")
    public Object logout( Integer userId) {
        if (userId == null) {
        }
        return R.ok();
    }


}
