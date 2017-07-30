package com.welkin.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.portal.utils.HttpClientUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/showLogin")
	public String showLogin() {
		return "redirect:/login.html";
	}
	
	@RequestMapping("/showRegister")
	public String showRegister() {
		return "redirect:/register.html";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(String username, String password) {
		
		String url = "http://localhost:8084/user/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		String jsonStr = HttpClientUtils.doPost(url, params);
		
		
		return jsonStr;
	}
}
