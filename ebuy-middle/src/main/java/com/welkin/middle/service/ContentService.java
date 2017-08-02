package com.welkin.middle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.welkin.commons.JsonUtils;
import com.welkin.mapper.TbContentMapper;
import com.welkin.dao.RedisDao;
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
	@Value("${TB_CONTENT_KEY}")
	private String TB_CONTENT_KEY;
	@Value("${CATEGORY_ID_FIELD}")
	private String CATEGORY_ID_FIELD;
	@Value("${AD_LIST_FIELD}")
	private String AD_LIST_FIELD;
	

	public String findById(Long cid) {
		String value = redis.hget(TB_CONTENT_KEY, CATEGORY_ID_FIELD + cid);
		if (value != null && !value.equals(""))
			return value;
		TbContentExample ex = new TbContentExample();
		Criteria c = ex.createCriteria();
		c.andCategoryIdEqualTo(cid);
		List<TbContent> li = tbContentMapper.selectByExample(ex);
		
		value = JsonUtils.objectToJson(li);
		redis.hset(TB_CONTENT_KEY, CATEGORY_ID_FIELD + cid, value);

		return value;
	}

	/**
	 * 功能：按内容分类编号查询对应的广告信息
	 * @param cateId 分类编号
	 * @return
	 */
	public String findAdList(Long catId) {
		// 读取缓冲
		String value = redis.hget(TB_CONTENT_KEY, AD_LIST_FIELD + catId);
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
		value = JsonUtils.objectToJson(mlist);
		redis.hset(TB_CONTENT_KEY,  AD_LIST_FIELD + catId, value);

		return value;
	}
}
