package com.welkin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.TPager;
import com.welkin.service.TbItemService;

@Controller
@RequestMapping("/item")
public class TbItemController {
	@Autowired
	private TbItemService tbItemser;

	@RequestMapping("/list")
	public @ResponseBody TPager findlist(int page, int rows) {
		System.out.println("page:" + page + ",rows:" + rows);
		TPager p = tbItemser.selectPager(page, rows);
		return p;
	}

}
