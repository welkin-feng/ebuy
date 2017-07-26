package com.welkin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.pojo.CateResult;
import com.welkin.service.ItemCatService;

@Controller
@RequestMapping("/itemcat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String findAllCat(String callback) {
		// 调用业务
		CateResult res = itemCatService.getAllCate();
		// 将java 对象转换成json字符串
		ObjectMapper om = new ObjectMapper();
		String s = "";
		try {
			s = om.writeValueAsString(res);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String str = callback + "(" + s + ");";
		System.out.println("middle str: " + str);

		return str;
	}

}
