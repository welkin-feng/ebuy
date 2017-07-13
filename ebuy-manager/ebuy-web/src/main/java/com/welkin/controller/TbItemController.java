package com.welkin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.service.TbItemService;

@Controller
@RequestMapping("/item")
public class TbItemController {
	@Autowired
	private TbItemService tbItemser;
 @RequestMapping("/list")
 public @ResponseBody Tpager findlist(int page,int rows){
	 System.out.println("page:"+page+",rows:"+rows);
	 Tpager p = tbItemser.findall(page, rows);
	 return p;
 }
 
}
