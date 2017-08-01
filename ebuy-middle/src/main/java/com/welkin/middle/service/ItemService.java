package com.welkin.middle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.commons.JsonUtils;
import com.welkin.mapper.TbItemDescMapper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.mapper.TbItemParamItemMapper;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemDescExample;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemExample.Criteria;
import com.welkin.pojo.TbItemParamItem;
import com.welkin.pojo.TbItemParamItemExample;

@Service
public class ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	/**
	 * 功能：根据ItemId查询TbItem，找到具体的商品，该商品的查询结果只能有一个
	 * 
	 * @param itemId
	 *            商品的id
	 * @return 商品实体码字符串
	 */
	public String queryItemByItemId(Long itemId) {
		TbItemExample ex = new TbItemExample();
		Criteria c = ex.createCriteria();
		c.andIdEqualTo(itemId);
		List<TbItem> tbItems = tbItemMapper.selectByExample(ex);

		if (1 == tbItems.size()) 
			return JsonUtils.objectToJson(tbItems.get(0));
		
		return "";
	}

	/**
	 * 功能：根据ItemId查询TbItemDesc，找到具体的商品详述，该商品的查询结果只能有一个
	 * 
	 * @param itemId
	 *            商品的id
	 * @return 商品实体码字符串
	 */
	public String queryItemDescByItemId(Long itemId) {
		TbItemDescExample ex = new TbItemDescExample();
		com.welkin.pojo.TbItemDescExample.Criteria c = ex.createCriteria();
		c.andItemIdEqualTo(itemId);
		List<TbItemDesc> tbItemDescs = tbItemDescMapper.selectByExampleWithBLOBs(ex);

		if (1 == tbItemDescs.size()) 
			return JsonUtils.objectToJson(tbItemDescs.get(0));
		
		return "";
	}

	/**
	 * 功能：根据ItemId查询TbItemParamItem，找到具体的商品规格，该商品的查询结果只能有一个
	 * 
	 * @param itemId
	 *            商品的id
	 * @return 商品实体码字符串
	 */
	public String queryItemParamItemByItemId(Long itemId) {
		TbItemParamItemExample ex = new TbItemParamItemExample();
		com.welkin.pojo.TbItemParamItemExample.Criteria c = ex.createCriteria();
		c.andItemIdEqualTo(itemId);
		List<TbItemParamItem> tbItemParamItem = tbItemParamItemMapper.selectByExampleWithBLOBs(ex);

		if (1 == tbItemParamItem.size()) 
			return JsonUtils.objectToJson(tbItemParamItem.get(0));
		
		return "";
	}
}
