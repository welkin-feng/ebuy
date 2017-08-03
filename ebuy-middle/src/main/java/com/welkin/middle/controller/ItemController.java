package com.welkin.middle.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.JsonUtils;
import com.welkin.middle.service.ItemService;
import com.welkin.pojo.TbItemCat;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 响应portal根据商品cid查询TbItemCat中的分类
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/query/itemCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findItemCategoryByItemCid(Long cid) {

		System.out.println("cid:" + cid);
		
		Long _cid = cid;
		List<String> categories = new ArrayList<String>();

		while(true) {
			String _tbItemCat = itemService.queryItemCategoryByItemCid(_cid);
			System.out.println(_tbItemCat);
			TbItemCat tbItemCat = JsonUtils.jsonToObject(_tbItemCat, TbItemCat.class);
			if(tbItemCat.getParentId() != 0) {
				categories.add(tbItemCat.getName());
				_cid = tbItemCat.getParentId();
				continue;
			}
			else {
				categories.add(tbItemCat.getName());
				break;
			}
		}
		
		String res = JsonUtils.objectToJson(categories);

		return res;
	}

	/**
	 * 响应portal根据商品id查询TbItem
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/query/item", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findItemByItemId(@RequestParam(value = "itemId", required = true) Long itemId) {
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
	public String findItemDescByItemId(@RequestParam(value = "itemId", required = true) Long itemId) {
		System.out.println("desc itemId:" + itemId);
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
	public String findItemParamItemByItemId(@RequestParam(value = "itemId", required = true) Long itemId) {
		String itemParamItem = itemService.queryItemParamItemByItemId(itemId);
		return itemParamItem;
	}

}
