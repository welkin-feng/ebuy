package com.welkin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.welkin.mapper.TbContentMapper;
import com.welkin.pojo.TbContent;
import com.welkin.pojo.TbContentExample;
import com.welkin.pojo.TbContentExample.Criteria;
import com.welkin.util.Pager;

@Service
public class ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;

	public int delete(ArrayList<Long> ids) {

		TbContentExample ex = new TbContentExample();
		Criteria c = ex.createCriteria();
		c.andIdIn(ids);
		return tbContentMapper.deleteByExample(ex);
	}

	public int update(TbContent con) {
		con.setUpdated(new Date());
		TbContentExample ex = new TbContentExample();
		Criteria c = ex.createCriteria();
		c.andIdEqualTo(con.getId());
		return tbContentMapper.updateByPrimaryKeySelective(con);
	}

	public int save(TbContent con) {
		con.setCreated(new Date());
		con.setUpdated(new Date());
		return tbContentMapper.insert(con);
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
