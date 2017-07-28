package com.welkin.middle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.mapper.TbContentMapper;
import com.welkin.middle.dao.RedisDao;
import com.welkin.middle.pojo.AdItem;
import com.welkin.pojo.TbContent;
import com.welkin.pojo.TbContentExample;
import com.welkin.pojo.TbContentExample.Criteria;

@Service
public class ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private RedisDao redis;

	public String findById(Long gid) {
		String value = redis.hgetKey("ContentService", gid + "");
		if (value != null && !value.equals(""))
			return value;
		TbContentExample ex = new TbContentExample();
		Criteria c = ex.createCriteria();
		c.andCategoryIdEqualTo(gid);
		List<TbContent> li = tbContentMapper.selectByExample(ex);
		ObjectMapper om = new ObjectMapper();
		try {
			value = om.writeValueAsString(li);
			// 写入缓冲区
			redis.hsetKey("ContentService", gid + "", value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * 功能：按内容分类编号查询对应的广告信息
	 * @param cateId 分类编号
	 * @return
	 */
	public String findAdList(Long catId) {
		// 读取缓冲
		String value = redis.hgetKey("ContentService", catId + "");
		if (value != null && !value.equals(""))
			return value;

		TbContentExample ex = new TbContentExample();
		// 按内容分类编号查询对应的广告信息
		Criteria c = ex.createCriteria();
		c.andCategoryIdEqualTo(catId);
		List<TbContent> li = tbContentMapper.selectByExampleWithBLOBs(ex);

		List<AdItem> mlist = new ArrayList<AdItem>();
		for (TbContent con : li) {
			// 广告信息的实体对象
			AdItem item = new AdItem();
			item.setSrc(con.getPic());
			item.setWidth(670);
			item.setHeight(240);
			item.setAlt(con.getTitle());
			item.setSrcB(con.getPic2());
			item.setWidthB(550);
			item.setHeightB(240);
			item.setHref(con.getUrl());
			mlist.add(item);
		}
		// 将java的集合对象转换成json字符串
		ObjectMapper om = new ObjectMapper();
		try {
			value = om.writeValueAsString(mlist);
			// 写入缓冲区
			redis.hsetKey("ContentService", catId + "", value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return value;
	}
}
