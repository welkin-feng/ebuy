package com.welkin.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemParamItem;
import com.welkin.service.TbItemOpService;
import com.welkin.service.TbItemService;
import com.welkin.util.Message;
import com.welkin.util.MessageUtil;

@Controller
@RequestMapping("/rest/item")
public class TbItemOpController {
	@Autowired
	private TbItemOpService tbItemOpService;
	@Autowired
	private TbItemService tbItemService;

	/**
	 * 编辑商品：根据用户选择具体商品id查询对应商品对象，返回给类型的json形式的数据
	 * @param id
	 * @return
	 */
	@RequestMapping("query/item/desc/{itemid}")
	@ResponseBody
	public Message findItemById(@PathVariable Long itemid) {
		Message mes = new Message();
		TbItemDesc itemdesc = tbItemService.findTbItemDescById(itemid);

		if (null != itemdesc) {
			mes.setData(itemdesc);
			mes.setStatus(200);
		} else
			mes.setStatus(500);
		return mes;
	}

	/**
	 * 编辑商品：根据用户选择具体商品的id查询TbItemParamItem表，得到规格信息
	 * @param paramitemid
	 * @return
	 */
	@RequestMapping("param/item/query/{paramitemid}")
	@ResponseBody
	public Message findItemParamItemById(@PathVariable Long paramitemid) {
		Message mes = new Message();
		TbItemParamItem paramitem = tbItemService.findItemParamItemById(paramitemid);

		if (null != paramitem) {
			mes.setData(paramitem);
			mes.setStatus(200);
		} else
			mes.setStatus(500);
		return mes;
	}

	// 删除商品
	@RequestMapping("/delete")
	@ResponseBody
	public Message delete(@RequestParam(value = "ids", required = true) ArrayList<Long> ids) {
		int x = tbItemOpService.delete(ids);
		return MessageUtil.generateStatus(x);
	}

	// 下架商品
	@RequestMapping("/instock")
	@ResponseBody
	public Message instock(@RequestParam(value = "ids", required = true) ArrayList<Long> ids) {
		int x = tbItemOpService.updateStock(ids, (byte) 2);
		return MessageUtil.generateStatus(x);
	}

	// 上架商品
	@RequestMapping("/reshelf")
	@ResponseBody
	public Message reshelf(@RequestParam(value = "ids", required = true) ArrayList<Long> ids) {
		int x = tbItemOpService.updateStock(ids, (byte) 1);
		return MessageUtil.generateStatus(x);
	}

	@RequestMapping("/update")
	@ResponseBody
	public Message update(TbItem po, @RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "itemParams", required = false) String itemParam) {
		System.out.println("商品描述:" + desc);
		System.out.println("商品规格:" + itemParam);

		int x = tbItemOpService.updateItem(po, desc, itemParam);
		return MessageUtil.generateStatus(x);
	}
}
