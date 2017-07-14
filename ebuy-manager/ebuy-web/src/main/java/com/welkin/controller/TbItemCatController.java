package com.welkin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.service.ItemCapService;



@Controller
@RequestMapping("/item/cat")
public class TbItemCatController {
  @Autowired
  private ItemCapService itemcapSer;
 /* @RequestMapping("/list")
  @ResponseBody
	public List<MTreeNode> getCatList(@RequestParam(value="id", defaultValue="0")long parentId) {
		List<MTreeNode> list = itemcapSer.findTrees(parentId);
		
		return list;
	}*/
}
