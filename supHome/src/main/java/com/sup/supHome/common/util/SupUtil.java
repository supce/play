package com.sup.supHome.common.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sup.supHome.common.beans.WeChat;
import com.sup.supHome.common.cache.EhCacheTemplate;
import com.sup.supHome.common.configuration.PropertiesConfig;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 调用订阅号工具
 * @desc 
 * @author Liuc
 * @date 2018年8月20日 下午5:16:08
 */
@Component
public class SupUtil {

	@Value("${accessTokenUrl}")
	private String accessTokenUrl;			// 获取accessToken接口URL
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	private Lock getTokenlock = new ReentrantLock();

    /**
     * 获取token
     * 默认值为wechat
     */
    public String getAccessToken(String agent) {
    	//默认值为wechat
    	agent = StringUtils.isEmpty(agent)?"wechat":agent;
    	//缓存名tokenName
    	String tokenName = Constants.ACCESS_TOKEN+agent;
        Cache accessTokenCache = EhCacheTemplate.getCache(Constants.ACCESS_TOKEN_CACHE);
        Element tokenElement = accessTokenCache.get(tokenName);
        if (null != tokenElement) {
            return (String) tokenElement.getObjectValue();
        }
        try {
        	getTokenlock.lock();
            tokenElement = accessTokenCache.get(tokenName);
            if (null != tokenElement) {
                return (String) tokenElement.getObjectValue();
            }
            // secret
            WeChat wechat = propertiesConfig.getWechat();
            String url = accessTokenUrl + "&appid=" + wechat.getAppid() + "&secret=" + wechat.getAppsecret();
            String result = HttpUtil.get(url);
            JSONObject jsonResult =JSONUtil.parseObj(result);
            Integer errcode = jsonResult.getInt("errcode");
            if (null == errcode) {
                String accessToken = jsonResult.getStr("access_token");
                accessTokenCache.put(new Element(tokenName, accessToken));
                return accessToken;
            } else {
                return jsonResult.getStr("errmsg");
            }
        } finally {
        	getTokenlock.unlock();
        }
    }
}
