package com.welkin.middle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.middle.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	/**
	 * 响应portal根据商品id查询TbItem
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/query/item", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findItemByItemId(Long itemId) {
		System.out.println("itemId:" + itemId);
		String item = itemService.queryItemByItemId(itemId);
		return item;
	}

	/**
	 * 响应portal根据商品id查询TbItemDesc
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/query/itemDesc", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findItemDescByItemId(Long itemId) {
		System.out.println("itemId:" + itemId);
		String itemDesc = itemService.queryItemDescByItemId(itemId);
		return itemDesc;
	}

	/**
	 * 响应portal根据商品id查询TbItemParamItem
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/query/itemParam", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findItemParamItemByItemId(Long itemId) {
		String itemParamItem = itemService.queryItemParamItemByItemId(itemId);
		return itemParamItem;
	}

}
