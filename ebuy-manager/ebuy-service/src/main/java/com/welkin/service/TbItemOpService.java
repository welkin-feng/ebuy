package com.welkin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.welkin.dao.RedisDao;
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
public class TbItemOpService {
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

	/**
	 * 删除商品 同时删除数据库中的“商品”和“商品对应的规格参数信息”
	 * 
	 * @param ids
	 *            要删除的商品id List
	 * @return 删除状态
	 * 
	 */
	public boolean delete(List<Long> ids) {
		TbItemExample itemEx = new TbItemExample();
		Criteria c1 = itemEx.createCriteria();
		c1.andIdIn(ids);
		int x1 = tbItemMapper.deleteByExample(itemEx);

		TbItemParamItemExample paramItemEx = new TbItemParamItemExample();
		com.welkin.pojo.TbItemParamItemExample.Criteria c2 = paramItemEx.createCriteria();
		c2.andItemIdIn(ids);
		tbItemParamItemMapper.deleteByExample(paramItemEx);
		int x2 = tbItemParamItemMapper.selectByExample(paramItemEx).size();

		TbItemDescExample itemDescEx = new TbItemDescExample();
		com.welkin.pojo.TbItemDescExample.Criteria c3 = itemDescEx.createCriteria();
		c3.andItemIdIn(ids);
		tbItemDescMapper.deleteByExample(itemDescEx);
		int x3 = tbItemDescMapper.selectByExample(itemDescEx).size();

		if (x1 > 0 && x2 == 0 && x3 == 0) {
			redis.del(TB_ITEM_KEY);
			redis.del(TB_ITEM_DESC_KEY);
			redis.del(TB_ITEM_PARAM_ITEM_KEY);
			return true;
		}
		return false;
	}

	/**
	 * 上、下架商品 更改商品在数据库中的 status参数的值，1代表已经上架，2代表已经下架
	 * 
	 * @param ids
	 * @return
	 */
	public int updateStock(List<Long> ids, Byte status) {
		TbItemExample ex = new TbItemExample();
		Criteria c = ex.createCriteria();
		c.andIdIn(ids);
		TbItem item = new TbItem();
		item.setStatus(status);
		item.setUpdated(new Date());

		int x = tbItemMapper.updateByExampleSelective(item, ex);
		if (x > 0)
			redis.del(TB_ITEM_KEY);
		return x;
	}

	public boolean updateItem(TbItem po, String des, String paramStr) {
		// 修改商品的更新时间
		po.setStatus((byte) 1);
		po.setUpdated(new Date());
		// 更新商品的信息
		TbItemExample ex1 = new TbItemExample();
		com.welkin.pojo.TbItemExample.Criteria c1 = ex1.createCriteria();
		c1.andIdEqualTo(po.getId());
		int x1 = tbItemMapper.updateByExampleSelective(po, ex1);
		int x2 = 1, x3 = 1;
		boolean flag2 = false, flag3 = false;

		if (des != null) {
			flag2 = true;
			// 修改商品描述
			TbItemDesc desc = new TbItemDesc();
			desc.setItemId(po.getId());
			desc.setUpdated(new Date());
			desc.setItemDesc(des);
			// 更新商品描述信息
			TbItemDescExample ex2 = new TbItemDescExample();
			com.welkin.pojo.TbItemDescExample.Criteria c2 = ex2.createCriteria();
			c2.andItemIdEqualTo(po.getId());
			x2 = tbItemDescMapper.updateByExampleSelective(desc, ex2);
		}

		if (paramStr != null) {
			flag3 = true;
			// 修改商品规格信息
			TbItemParamItem paramItem = new TbItemParamItem();
			paramItem.setItemId(po.getId());
			paramItem.setParamData(paramStr);
			paramItem.setUpdated(new Date());
			// 更新商品规格信息
			TbItemParamItemExample ex3 = new TbItemParamItemExample();
			com.welkin.pojo.TbItemParamItemExample.Criteria c3 = ex3.createCriteria();
			c3.andItemIdEqualTo(po.getId());
			int count = tbItemParamItemMapper.countByExample(ex3);
			if (count > 0)
				x3 = tbItemParamItemMapper.updateByExampleSelective(paramItem, ex3);
			else {
				paramItem.setCreated(new Date());
				x3 = tbItemParamItemMapper.insertSelective(paramItem);
			}
		}

		if ((x1 & x2 & x3) > 0) {
			redis.del(TB_ITEM_KEY);
			if (flag2)
				redis.del(TB_ITEM_DESC_KEY);
			if (flag3)
				redis.del(TB_ITEM_PARAM_ITEM_KEY);
			return true;
		}
		return false;
	}

}
