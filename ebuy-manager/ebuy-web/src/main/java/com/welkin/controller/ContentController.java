package com.welkin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.Message;
import com.welkin.pojo.Pager;
import com.welkin.pojo.TbContent;
import com.welkin.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public Message save(TbContent con) {
		Message m = new Message();
		int x = contentService.save(con);
		if(x > 0) 
			m.setStatus(200);
		else
			m.setStatus(500);
		return m;
	}
	
	@RequestMapping("/query/list")
	@ResponseBody
	public Pager findByCid(Long categoryId, int page, int rows) {
		Pager p = contentService.selectPager(categoryId, page, rows);
		return p;
	}

}
