package com.welkin.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.TPager;
import com.welkin.pojo.TbItemCat;
import com.welkin.pojo.TreeNode;
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
	public List<TreeNode> findCaps(@RequestParam(value = "cid", defaultValue = "0") Long parent_id) {
		List<TbItemCat> li = tbItemser.findByParentId(parent_id);
		
		List<TreeNode> mli=new ArrayList<TreeNode>();
		//遍历分类集合
		for (TbItemCat cat :li) {
			//用于页面显示数据的封装  id,text,status
			TreeNode m=new TreeNode();
			m.setId(cat.getId());
			m.setText(cat.getName());
			m.setState(cat.getIsParent()?"closed":"open");
			//将分类节点数据封装到集合中
			mli.add(m);
		}
		System.out.println("商品分类" + li.size());
		
		return mli;
	}
	
}
