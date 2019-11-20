package com.cn.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.utils.R;
import com.cn.common.utils.ShiroUtils;
import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.form.SysLoginForm;
import com.cn.modules.sys.service.SysCaptchaService;
import com.cn.modules.sys.service.SysUserTokenService;
import com.cn.modules.sys.service.SystemUserService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 * 
 * @author chenshun
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	@GetMapping("/getCode")
	public String getCode(String uuid){
		return sysCaptchaService.getCode(uuid);
	}
	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public Map<String, Object> login(@RequestBody SysLoginForm form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return R.error("验证码不正确");
		}
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

	/**
	 * 登录
	 */
	@GetMapping("/auth/info")
	public Object getUserInfo() {
		SystemUserEntity user =  getUser();
		return ResponseEntity.ok(user);
	}

	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUser());
		return R.ok();
	}
	
}
