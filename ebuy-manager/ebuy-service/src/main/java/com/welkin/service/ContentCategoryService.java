package com.welkin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.mapper.TbContentCategoryMapper;
import com.welkin.pojo.TbContentCategory;
import com.welkin.pojo.TbContentCategoryExample;
import com.welkin.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	public int save(TbContentCategory cc) {
		cc.setIsParent(false);
		cc.setStatus(1);
		cc.setSortOrder(1);
		cc.setCreated(new Date());
		cc.setUpdated(new Date());
		/* 注意：当Mapper中的insert中加入selectKey后，会自动为实体对象的主键进行赋值 */
		int x1 = tbContentCategoryMapper.insert(cc);
		int x2 = 1;
		// 将当前新结点对应的父节点的 IsParent 属性进行更改
		TbContentCategory pcc = tbContentCategoryMapper.selectByPrimaryKey(cc.getParentId());
		if(!pcc.getIsParent()){
			pcc.setIsParent(true);
			pcc.setUpdated(new Date());
			x2 = tbContentCategoryMapper.updateByPrimaryKeySelective(pcc);
		}
		
		return x1 & x2;
	}
	
	public List<TbContentCategory> findByParentId(Long id) {
		TbContentCategoryExample ex = new TbContentCategoryExample();
		Criteria c = ex.createCriteria();
		c.andParentIdEqualTo(id);
		return tbContentCategoryMapper.selectByExample(ex);
	}

}
