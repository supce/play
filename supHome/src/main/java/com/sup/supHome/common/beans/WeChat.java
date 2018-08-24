package com.sup.supHome.common.beans;

/**
 * 订阅号
 * @desc 
 * @author Liuchen
 * @date 2018年8月20日 下午5:09:58
 */
public class WeChat {

	private String appid;
	
	private String appsecret;
	
	private String token;
	
	private String encodingaeskey;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingaeskey() {
		return encodingaeskey;
	}

	public void setEncodingaeskey(String encodingaeskey) {
		this.encodingaeskey = encodingaeskey;
	}
	
}
