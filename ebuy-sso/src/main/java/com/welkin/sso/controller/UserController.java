package com.welkin.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.sso.service.UserService;
import com.welkin.commons.JsonUtil;
import com.welkin.commons.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/showLogin")
	public String showLogin() {
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/doRegist", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String doRegist(String username, String password, String phone) {
		Message m = new Message();
		int rv = userService.doRegist(username, password, phone);
		if(rv > 0)
			m.setStatus(200);
		else 
			m.setStatus(400);
		
		return JsonUtil.messageToString(m);
	}
	
	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String doLogin(String username, String password) {
		Message m = userService.login(username, password);
		
		System.out.println("status:" + m.getStatus());
//		System.out.println("msg:" + m.getMsg());
		
		return JsonUtil.messageToString(m);
	}
	
}
