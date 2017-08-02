package com.welkin.middle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.Message;
import com.welkin.middle.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/userInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findUser(@RequestParam(value = "userName") String userName) {
		Message me = userService.findUserByUserName(userName);

		if (me.getStatus() == 200) {
			return (String) me.getData();
		}

		return "";
	}
}
