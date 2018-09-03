package com.sup.supHome.controller;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sup.supHome.common.configuration.PropertiesConfig;
import com.sup.supHome.common.message.MessageHandlerUtil;
import com.sup.supHome.common.util.SupUtil;

import cn.hutool.crypto.SecureUtil;

@RequestMapping("/system")
@Controller
public class SystemController {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

	@Autowired
	private PropertiesConfig propertiesConfig;
	@Autowired
	private SupUtil supUtil;

	@RequestMapping(value="replay")
	@ResponseBody
	public String replay(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		for(String key : map.keySet()) {
			logger.info(key);
		}
		
		//String appid = propertiesConfig.getWechat().getAppid();
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String token = propertiesConfig.getWechat().getToken();
		//String postdata = request.getParameter("postdata");
		//String encodingaeskey = propertiesConfig.getWechat().getEncodingaeskey();
		//String openid = request.getParameter("openid");
		
		//参数为空直接返回false
		if(StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(signature)) {
			return "false";
		}
		boolean flag = judgeSignature(token,timestamp,nonce,signature);
		if(flag) {
			//验证通过
			try {
				Map<String, String> parseXml = MessageHandlerUtil.parseXml(request);
				logger.info("接收到消息："+parseXml);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("解析消息失败："+e);
			}
			return echostr;
		}
		
		return "false";
	}
	
	/**
	 * 
	 * @desc：检验签名
	 *
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return 
	 * 
	 * @author：Liuc
	 * @date:2018年9月3日 下午6:02:15
	 */
	private boolean judgeSignature(String token,String timestamp,String nonce,String signature) {
		//字典顺序
		Set<String> set = new TreeSet<>();
		set.add(token);
		set.add(timestamp);
		set.add(nonce);
		
		StringBuffer sb = new StringBuffer();
		for (String str : set) {
			sb.append(str);
		}
		
		//生产sha1
    	String sha1 = SecureUtil.sha1(sb.toString());
		if(signature.equals(sha1)) {
			return true;
		}
		return false;
	}
	
}
