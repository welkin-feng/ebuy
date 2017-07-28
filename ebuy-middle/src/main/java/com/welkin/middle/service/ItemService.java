package com.welkin.middle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemExample.Criteria;

@Service
public class ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	
	public String queryItem(String query, Integer page) {
//		Pager pager = new Pager();
		PageHelper.startPage(page, 30);
		TbItemExample ex = new TbItemExample();
		Criteria c = ex.createCriteria();
		c.andTitleLike(query);
		List<TbItem> li = tbItemMapper.selectByExample(ex);
		
		ObjectMapper om = new ObjectMapper();
		String s = null;
		try {
			s = om.writeValueAsString(li);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
//		PageInfo<TbItem> pi = new PageInfo<>(li);
		
//		pager.setRows(li);
//		pager.setTotal(pi.getTotal());
		return s;
	}
}
