package com.welkin.middle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.middle.service.ItemCatService;

@Controller
@RequestMapping("/itemcat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String findAllCat(String callback) {
		// 调用业务
		String s = itemCatService.getAllCat();

		return callback + "(" + s + ");";
	}

}
