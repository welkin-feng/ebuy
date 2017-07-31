package com.welkin.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.welkin.commons.CookieUtils;

@Controller
public class UserController {

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		return "index";
	}

}
