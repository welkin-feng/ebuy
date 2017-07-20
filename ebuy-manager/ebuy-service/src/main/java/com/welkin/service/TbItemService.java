package com.welkin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.welkin.mapper.TbItemCatMapper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.pojo.TPager;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemCat;
import com.welkin.pojo.TbItemCatExample;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemExample.Criteria;

@Service
public class TbItemService {
	@Autowired
	private TbItemMapper tbItemdao;
	@Autowired
	private TbItemCatMapper catMapper;
	
	//查询所有商品信息
	public List<TbItem> findall(){
		//mapping生成Example对象查询条件对象的模板
		TbItemExample ex=new TbItemExample();
		Criteria c = ex.createCriteria();	//生成查询条件
		c.andTitleLike("%电%");
		List<TbItem> li = tbItemdao.selectByExample(ex);
		return li;
	}

	/**
	 * 功能分页查询商品
	 * @param page	当前页码
	 * @param rows	每页显示记录数量
	 * @return	封装了分页信息的对象
	 */
	public TPager selectPager(Integer page, Integer rows) {
		TPager pager = new TPager();
		TbItemExample ex = new TbItemExample();
//		Criteria cri = ex.createCriteria();
		
		// 查询前开始分页
		// page: 第几页, rows：每页的记录数量
		PageHelper.startPage(page, rows);
		List<TbItem> li = tbItemdao.selectByExample(ex);
		// 获取查询集合
		// PageInfo<要查询的实体类> pi = new PageInfo<>(查询后的集合对象);
		PageInfo<TbItem> pi = new PageInfo<>(li);
		
		pager.setRows(li);
		pager.setTotal(pi.getTotal());
		return pager;
	}
	
	public List<TbItemCat> findByParentId(Long parent_id) {
		TbItemCatExample ex = new TbItemCatExample();
		// 根据查询对象模板生成条件对象
		com.welkin.pojo.TbItemCatExample.Criteria c = ex.createCriteria();
		// 按父类id查询下级所有分类对象
		c.andParentIdEqualTo(parent_id);
		return catMapper.selectByExample(ex);
	}
	
	public String save(TbItem po) {
		int x = tbItemdao.insert(po);
		Map<String, Integer> m = new HashMap<String, Integer>();
		if(x > 0) {
			m.put("status", 200);
		} else {
			m.put("status", 500);
		}
		/*
		 * jackson 组件将java 对象和 json 对象相互转化
		 * spring 中加入 jackson 组件，并且控制器返回直接 @ResponseBody 自动完成
		 * 将java 对象转换成 json字符串的工作，但是在不同版本浏览器中存在兼容问题
		 * 为了解决这个通过jackson 组件手工将java对象转换成json字符串
		 * ObjectMapper 提供将java对象转换成json字符串的方法 writeValueAsString
		 * */
		ObjectMapper om = new ObjectMapper();
		String s = null;
		try {
			s = om.writeValueAsString(m);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return s;
	}
}
