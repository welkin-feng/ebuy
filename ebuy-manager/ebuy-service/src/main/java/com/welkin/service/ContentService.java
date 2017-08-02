package com.welkin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.welkin.commons.Pager;
import com.welkin.dao.RedisDao;
import com.welkin.mapper.TbContentMapper;
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

	public int delete(ArrayList<Long> ids) {
		TbContentExample ex = new TbContentExample();
		Criteria c = ex.createCriteria();
		c.andIdIn(ids);
		int x = tbContentMapper.deleteByExample(ex);
		if (x > 0)
			redis.del(TB_CONTENT_KEY);
		return x;
	}

	public int update(TbContent con) {
		con.setUpdated(new Date());
		TbContentExample ex = new TbContentExample();
		Criteria c = ex.createCriteria();
		c.andIdEqualTo(con.getId());
		int x = tbContentMapper.updateByPrimaryKeySelective(con);
		if (x > 0)
			redis.del(TB_CONTENT_KEY);
		return x;
	}

	public int save(TbContent con) {
		con.setCreated(new Date());
		con.setUpdated(new Date());
		int x = tbContentMapper.insert(con);
		if (x > 0)
			redis.del(TB_CONTENT_KEY);
		return x;
	}

	public Pager selectPager(Long cid, int page, int rows) {
		Pager pager = new Pager();
		PageHelper.startPage(page, rows);
		TbContentExample ex = new TbContentExample();
		Criteria c = ex.createCriteria();
		c.andCategoryIdEqualTo(cid);
		List<TbContent> li = tbContentMapper.selectByExampleWithBLOBs(ex);

		PageInfo<TbContent> pi = new PageInfo<>(li);

		pager.setRows(li);
		pager.setTotal(pi.getTotal());
		return pager;
	}

}
