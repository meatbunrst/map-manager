package com.cn.common.utils;

import com.cn.common.exception.RRException;
import com.cn.modules.sys.entity.SystemUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SystemUserEntity getUserEntity() {
		return (SystemUserEntity)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new RRException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}

	public static String getDecrypt(String decryptedPassword){

		StringBuffer buffer = new StringBuffer();
		AesUtil aesUtil = new AesUtil(128, 1000);
		if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
			String password = aesUtil.decrypt(decryptedPassword.split("::")[1], decryptedPassword.split("::")[0], "", decryptedPassword.split("::")[2]);
			buffer.append(password);
		}

		return buffer.toString();
	}

	public static String getDecrypt(String decryptedPassword,String uuid){

		StringBuffer buffer = new StringBuffer();
		AesUtil aesUtil = new AesUtil(128, 1000);
		if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
			String password = aesUtil.decrypt(decryptedPassword.split("::")[1], decryptedPassword.split("::")[0], uuid, decryptedPassword.split("::")[2]);
			buffer.append(password);
		}

		return buffer.toString();
	}
}
