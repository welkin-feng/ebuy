package com.welkin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller                                                                                                                                                        
public class PagerUtil {
	@RequestMapping("/{pager}")
	public String gopager(@PathVariable String pager) {
		return pager;
	}
	
	@RequestMapping("/index")
	public String index() {
		System.out.println("首页信息");
		return "index";
	}
}
