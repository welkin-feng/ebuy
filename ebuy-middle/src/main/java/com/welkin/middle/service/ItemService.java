package com.welkin.middle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.welkin.mapper.TbItemDescMapper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.mapper.TbItemParamItemMapper;
import com.welkin.middle.pojo.MItemPager;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemDescExample;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemExample.Criteria;
import com.welkin.pojo.TbItemParamItem;
import com.welkin.pojo.TbItemParamItemExample;

@Service
public class ItemService {
	private static final int PAGESIZE = 6;
	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	/**
	 * 功能：按条件分页查询商品
	 * 
	 * @param query
	 *            查询条件
	 * @param page
	 *            显示的页码
	 * @return
	 */
	public String queryItem(String query, Integer page) {

		// 分页
		PageHelper.startPage(page, PAGESIZE);
		// 按条件查询

		TbItemExample ex = new TbItemExample();
		Criteria c = ex.createCriteria();

		c.andTitleLike("%" + query + "%");
		List<TbItem> tbItems = tbItemMapper.selectByExample(ex);
		// 分页信息
		PageInfo<TbItem> pi = new PageInfo<TbItem>(tbItems);
		Long rescount = pi.getTotal();
		// 计算总页数
		Integer pagecount = rescount.intValue() % PAGESIZE == 0 ? rescount.intValue() / PAGESIZE
				: rescount.intValue() / PAGESIZE + 1;

		MItemPager mp = new MItemPager();
		mp.setItemList(tbItems);
		mp.setPage(page);
		mp.setQuery(query);
		mp.setTotalPages(pagecount);
		String str = null;

		ObjectMapper om = new ObjectMapper();
		try {
			str = om.writeValueAsString(mp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}


		return str;
	}

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

		if (1 == tbItems.size()) {
			ObjectMapper om = new ObjectMapper();
			String str = "";
			try {
				str = om.writeValueAsString(tbItems.get(0));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return str;
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
		TbItemDescExample ex = new TbItemDescExample();
		com.welkin.pojo.TbItemDescExample.Criteria c = ex.createCriteria();
		c.andItemIdEqualTo(itemId);
		List<TbItemDesc> tbItemDescs = tbItemDescMapper.selectByExampleWithBLOBs(ex);

		if (1 == tbItemDescs.size()) {
			ObjectMapper om = new ObjectMapper();
			String str = "";
			try {
				str = om.writeValueAsString(tbItemDescs.get(0));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return str;
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
		TbItemParamItemExample ex = new TbItemParamItemExample();
		com.welkin.pojo.TbItemParamItemExample.Criteria c = ex.createCriteria();
		c.andItemIdEqualTo(itemId);
		List<TbItemParamItem> tbItemParamItem = tbItemParamItemMapper.selectByExampleWithBLOBs(ex);

		if (1 == tbItemParamItem.size()) {
			ObjectMapper om = new ObjectMapper();
			String str = "";
			try {
				str = om.writeValueAsString(tbItemParamItem.get(0));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return str;
		}
		return "";
	}
}
