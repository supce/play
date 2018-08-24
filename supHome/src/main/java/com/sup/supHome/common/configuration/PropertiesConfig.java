package com.sup.supHome.common.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.sup.supHome.common.beans.WeChat;

@Component
@ConfigurationProperties(prefix = "sup") 
@PropertySource(value = {"classpath:application.properties"},encoding="utf-8")  
public class PropertiesConfig {

	public WeChat wechat;
	public Map<String, String> agentIdMap = new HashMap<String, String>();
	public Map<String, String> secretMap = new HashMap<String, String>();
	
	public WeChat getWechat() {
		return wechat;
	}

	public void setWechat(WeChat wechat) {
		this.wechat = wechat;
	}

	public Map<String, String> getAgentIdMap() {
		return agentIdMap;
	}

	public void setAgentIdMap(Map<String, String> agentIdMap) {
		this.agentIdMap = agentIdMap;
	}

	public Map<String, String> getSecretMap() {
		return secretMap;
	}

	public void setSecretMap(Map<String, String> secretMap) {
		this.secretMap = secretMap;
	}

}