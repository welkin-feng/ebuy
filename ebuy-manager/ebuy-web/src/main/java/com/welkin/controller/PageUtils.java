package com.welkin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageUtils {
	// 通过页面请求，自动跳转到 /WEB-INF/pager/
	@RequestMapping("/{pager}")
	public String execute(@PathVariable String pager) {
		System.out.println("pager 拦截：" + pager);
		return pager;
	}

}
