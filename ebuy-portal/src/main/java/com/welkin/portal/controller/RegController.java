package com.welkin.portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.TbUser;
import com.welkin.portal.utils.HttpClientUtils;
import com.welkin.commons.JsonUtil;
import com.welkin.commons.Message;

@Controller
@RequestMapping("/reg")
public class RegController {

	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(@RequestParam(value="name") String name) {
		
//		System.out.println("check name:" + name);

		Message m = new Message();
		m.setStatus(200);
		
		return JsonUtil.messageToString(m);
	}
	
	@RequestMapping("/checkPhone")
	@ResponseBody
	public String checkPhone(@RequestParam(value="phone") String phone) {
		
//		System.out.println("phone:" + phone);
		
		Message m = new Message();
		m.setStatus(200);
		
		return JsonUtil.messageToString(m);
	}
	
	@RequestMapping("/doRegist")
	@ResponseBody
	public String doRegist(TbUser user) {

		String url = "http://localhost:8084/user/doRegist";
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		params.put("password", DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		params.put("phone", user.getPhone());
		String jsonStr = HttpClientUtils.doPost(url, params);
		
		return jsonStr;
	}
	
}
