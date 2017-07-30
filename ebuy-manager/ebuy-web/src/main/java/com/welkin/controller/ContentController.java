package com.welkin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.commons.Pager;
import com.welkin.pojo.TbContent;
import com.welkin.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/delete")
	@ResponseBody
	public Message delete(@RequestParam(value = "ids", required = true) ArrayList<Long> ids) {
		// System.out.println("delete:" + "ids=" + ids);
		int x = contentService.delete(ids);
		return MessageUtil.generateStatus(x);
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Message update(TbContent con) {
		// System.out.println("editting!");
		// System.out.println("title:" + con.getTitle() + ",content:" +
		// con.getContent());

		int x = contentService.update(con);

		return MessageUtil.generateStatus(x);
	}

	@RequestMapping("/save")
	@ResponseBody
	public Message save(TbContent con) {
		int x = contentService.save(con);
		return MessageUtil.generateStatus(x);
	}

	@RequestMapping("/query/list")
	@ResponseBody
	public Pager findByCid(Long categoryId, int page, int rows) {
		Pager p = contentService.selectPager(categoryId, page, rows);
		return p;
	}

}
