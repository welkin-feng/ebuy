package com.welkin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.TPager;
import com.welkin.service.TbItemService;

@Controller
@RequestMapping("/item")
public class TbItemController {
	@Autowired
	private TbItemService tbItemser;

	@RequestMapping("/list")
	// spring 提供了 @ResponseBody 注解将java的对象自动转换成json对象并发送给客户端
	@ResponseBody
	public TPager findlist(int page, int rows) {
		System.out.println("page:" + page + ",rows:" + rows);
		TPager p = tbItemser.selectPager(page, rows);
		return p;
	}
	
	// 针对商品分类查询的 mapping
	@RequestMapping("/cat/list")
	@ResponseBody
	public List<Map<String, Object>> findCaps(@RequestParam(value = "cid", defaultValue = "0") Long parent_id) {
		List<Map<String, Object>> map = tbItemser.findItemCap(parent_id);
		
		return map;
	}
	
}
