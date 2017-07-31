package com.welkin.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.welkin.commons.CookieUtils;


@Controller
@RequestMapping("/page")
public class PageController {
	

	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/login")
	public String showLogin(String redirect, Model model) {
		System.out.println("redirect:" + redirect);
		
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
	
	
}
