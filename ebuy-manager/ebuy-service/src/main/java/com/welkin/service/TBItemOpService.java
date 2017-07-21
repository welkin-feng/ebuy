package com.welkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.mapper.TbItemMapper;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemExample.Criteria;

@Service
public class TBItemOpService {
	@Autowired
	private TbItemMapper tbItemMapper;

	public int delete(List<Long> ids) {
		TbItemExample ex = new TbItemExample();

		Criteria c = ex.createCriteria();
		c.andIdIn(ids);

		return tbItemMapper.deleteByExample(ex);

	}

}

