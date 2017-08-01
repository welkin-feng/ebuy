package com.welkin.portal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.welkin.portal.utils.HttpClientUtils;

@Controller                                                                                                                                                        
public class IndexController {
	@Value("${MIDDLE_URL}")
	private String MIDDLE_URL;
	@Value("${MIDDLE_ADLIST_URL}")
	private String MIDDLE_ADLIST_URL;
	
	@RequestMapping("/{pager}")
	public String gopager(@PathVariable String pager) {
		return pager;
	}
	
	@RequestMapping("/index")
	public String index(Model model) {
		System.out.println("首页信息");
		String url = MIDDLE_URL + MIDDLE_ADLIST_URL;
		// 通过工具类中 doGet 方法发送请求，并接收返回的数据
		String jsonArray = HttpClientUtils.doGet(url);
		//将返回的json数组格式的字符串封装Model给 页面传值
		model.addAttribute("ad1", jsonArray);
		return "index";
	}
}
