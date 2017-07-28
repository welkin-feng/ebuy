package com.welkin.middle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.middle.service.ContentService;
import com.welkin.pojo.Message;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public Message findbycid(@PathVariable Long contentCategoryId) {
		Message m = new Message();
		String s = contentService.findById(contentCategoryId);
		m.setData(s);
		m.setStatus(200);
		return m;
	}

	@RequestMapping(value = "/ADList/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String findAdlist(@PathVariable Long categoryId) {
		return contentService.findAdList(categoryId);
	}

}
