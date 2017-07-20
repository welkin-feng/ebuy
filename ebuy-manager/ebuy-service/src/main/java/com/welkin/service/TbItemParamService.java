package com.welkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.mapper.TbItemParamMapper;
import com.welkin.pojo.TbItemParam;
import com.welkin.pojo.TbItemParamExample;
import com.welkin.pojo.TbItemParamExample.Criteria;

@Service
public class TbItemParamService {
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	
	/**
	 * 功能：按商品类型的编号查询商品类型规格表，返回具体类型对应商品规格对象
	 * @param id 商品类型id
	 * @return 返回具体类型对应商品规格对象
	 */
	public TbItemParam findById(Long id) {
		TbItemParamExample ex = new TbItemParamExample();
		// 按产品类型编号查询
		Criteria c = ex.createCriteria();
		c.andItemCatIdEqualTo(id);
		List<TbItemParam> li = tbItemParamMapper.selectByExampleWithBLOBs(ex);
		if (li == null || li.size() < 1)
			return null;
		return li.get(0);
	}
	
	/**
	 * 功能：保存商品类型规格基本信息
	 * @param po 商品类型对应基本规格对象
	 * @return 
	 */
	public boolean save(TbItemParam po) {
		int x = tbItemParamMapper.insert(po);
		if(x > 0) 
			return true;
		else
			return false;
	}
}
