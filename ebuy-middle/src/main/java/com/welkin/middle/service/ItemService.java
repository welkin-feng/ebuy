package com.welkin.middle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.welkin.commons.JsonUtils;
import com.welkin.dao.RedisDao;
import com.welkin.mapper.TbItemDescMapper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.mapper.TbItemParamItemMapper;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemDescExample;
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
	@Autowired
	private RedisDao redis;
	@Value("${TB_ITEM_KEY}")
	private String TB_ITEM_KEY;
	@Value("${TB_ITEM_DESC_KEY}")
	private String TB_ITEM_DESC_KEY;
	@Value("${TB_ITEM_PARAM_ITEM_KEY}")
	private String TB_ITEM_PARAM_ITEM_KEY;
	@Value("${ITEM_ID_FIELD}")
	private String ITEM_ID_FIELD;

	/**
	 * 功能：根据ItemId查询TbItem，找到具体的商品，该商品的查询结果只能有一个
	 * 
	 * @param itemId
	 *            商品的id
	 * @return 商品实体码字符串
	 */
	public String queryItemByItemId(Long itemId) {
		String value = redis.hget(TB_ITEM_KEY, ITEM_ID_FIELD + itemId);
		if (value != null && !value.equals("")) {
			return value;
		}

		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);

		if (tbItem != null) {
			value = JsonUtils.objectToJson(tbItem);
			redis.hset(TB_ITEM_KEY, ITEM_ID_FIELD + itemId, value);
			return value;
		}
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
		String value = redis.hget(TB_ITEM_DESC_KEY, ITEM_ID_FIELD + itemId);
		if (value != null && !value.equals("")) {
			return value;
		}

		TbItemDescExample ex = new TbItemDescExample();
		com.welkin.pojo.TbItemDescExample.Criteria c = ex.createCriteria();
		c.andItemIdEqualTo(itemId);
		List<TbItemDesc> tbItemDescs = tbItemDescMapper.selectByExampleWithBLOBs(ex);

		if (1 == tbItemDescs.size()) {
			value = JsonUtils.objectToJson(tbItemDescs.get(0));
			redis.hset(TB_ITEM_DESC_KEY, ITEM_ID_FIELD + itemId, value);
			return value;
		}
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
		String value = redis.hget(TB_ITEM_PARAM_ITEM_KEY, ITEM_ID_FIELD + itemId);
		if (value != null && !value.equals("")) {
			return value;
		}

		TbItemParamItemExample ex = new TbItemParamItemExample();
		com.welkin.pojo.TbItemParamItemExample.Criteria c = ex.createCriteria();
		c.andItemIdEqualTo(itemId);
		List<TbItemParamItem> tbItemParamItem = tbItemParamItemMapper.selectByExampleWithBLOBs(ex);

		if (1 == tbItemParamItem.size()) {
			value = JsonUtils.objectToJson(tbItemParamItem.get(0));
			redis.hset(TB_ITEM_PARAM_ITEM_KEY, ITEM_ID_FIELD + itemId, value);
			return value;
		}
		return "";
	}
}
