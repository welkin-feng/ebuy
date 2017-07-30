package com.welkin.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.commons.Pager;
import com.welkin.commons.TreeNode;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemCat;
import com.welkin.service.TbItemService;

@Controller
@RequestMapping("/item")
public class TbItemController {
	@Autowired
	private TbItemService tbItemService;

	@RequestMapping("/save")
	@ResponseBody
	public Message save(TbItem po, @RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "itemParams", required = false) String params) {
		int x = tbItemService.save(po, desc, params);
		return MessageUtil.generateStatus(x);

		/*
		 * jackson 组件将java 对象和 json 对象相互转化 spring 中加入 jackson
		 * 组件，并且控制器返回直接 @ResponseBody 自动完成 将java 对象转换成
		 * json字符串的工作，但是在不同版本浏览器中存在兼容问题 为了解决这个通过jackson 组件手工将java对象转换成json字符串
		 * ObjectMapper 提供将java对象转换成json字符串的方法 writeValueAsString
		 */

	}

	@RequestMapping("/list")
	// spring 提供了 @ResponseBody 注解将java的对象自动转换成json对象并发送给客户端
	@ResponseBody
	public Pager itemlist(int page, int rows) {
		System.out.println("page:" + page + ",rows:" + rows);
		return tbItemService.selectPager(page, rows);
	}

	// 针对商品分类查询的 mapping
	@RequestMapping("/cat/list")
	@ResponseBody
	public List<TreeNode> findCats(@RequestParam(value = "id", defaultValue = "0") Long parent_id) {
		System.out.println("parent id: " + parent_id);
		List<TbItemCat> li = tbItemService.findByParentId(parent_id);

		List<TreeNode> mli = new ArrayList<TreeNode>();
		// 遍历分类集合
		for (TbItemCat cat : li) {
			// 用于页面显示数据的封装 id,text,status
			TreeNode m = new TreeNode();
			m.setId(cat.getId());
			m.setText(cat.getName());
			m.setState(cat.getIsParent() ? "closed" : "open");
			// 将分类节点数据封装到集合中
			mli.add(m);
		}
		System.out.println("商品分类" + li.size());

		return mli;
	}

}
