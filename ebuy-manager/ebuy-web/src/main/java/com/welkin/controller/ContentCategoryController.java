package com.welkin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.Message;
import com.welkin.pojo.TbContentCategory;
import com.welkin.pojo.TreeNode;
import com.welkin.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/create")
	@ResponseBody
	public Message create(Long parentId, String name) {
		Message m = new Message();
		// 封装用户传递的信息
		TbContentCategory cc = new TbContentCategory();
		cc.setParentId(parentId);
		cc.setName(name);
		int x = contentCategoryService.save(cc);
		if(x > 0)
			m.setStatus(200);
		else
			m.setStatus(500);
		return m;		
	}
	
	// 查询返回内容分类的信息
	@RequestMapping("/list")
	@ResponseBody
	public List<TreeNode> list(@RequestParam(value = "id", defaultValue = "0") Long id) {
		List<TreeNode> tli = new ArrayList<TreeNode>();
		List<TbContentCategory> li = contentCategoryService.findByParentId(id);
		for (TbContentCategory cat : li) {
			TreeNode m = new TreeNode();
			m.setId(cat.getId());
			m.setText(cat.getName());
			m.setState(cat.getIsParent() ? "closed" : "open");
			// 将分类节点数据封装到集合中
			tli.add(m);
		}
		System.out.println("内容分类" + li.size());
		return tli;
	}
}
