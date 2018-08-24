package com.sup.supHome.controller;

import java.io.StringReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.sup.supHome.common.configuration.PropertiesConfig;
import com.sup.supHome.common.util.SupUtil;

import cn.hutool.crypto.SecureUtil;

@RequestMapping("/system")
@Controller
public class SystemController {

	@Autowired
	private PropertiesConfig propertiesConfig;
	@Autowired
	private SupUtil supUtil;

	@RequestMapping(value="replay")
	@ResponseBody
	public String replay(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		for(String key : map.keySet()) {
			System.out.println(key);
		}
		
		//String appid = propertiesConfig.getWechat().getAppid();
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String token = "ionrdf3whojfvq3itle1g6wubvmjnx";
		//String postdata = request.getParameter("postdata");
		//String encodingAesKey = "xKXBxuGN3xYsYcIFJBua7XVDGMi64CulNW6OT9WWLWh";
		
		//字典顺序
		Set<String> set = new TreeSet<>();
		set.add(token);
		set.add(timestamp);
		set.add(nonce);
		
		StringBuffer sb = new StringBuffer();
		for (String str : set) {
			sb.append(str);
		}
		
		//decryptMsg(String msgSignature, String timeStamp, String nonce, String postData)
		
		//生产sha1
    	String sha1 = SecureUtil.sha1(sb.toString());
		if(signature.equals(sha1)) {
			return echostr;
		}
		
		return "false";
	}
	
}
