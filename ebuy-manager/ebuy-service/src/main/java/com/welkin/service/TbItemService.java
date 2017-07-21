package com.welkin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.welkin.mapper.TbItemCatMapper;
import com.welkin.mapper.TbItemDescMapper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.mapper.TbItemParamItemMapper;
import com.welkin.mapper.TbItemParamMapper;
import com.welkin.pojo.TMessage;
import com.welkin.pojo.TPager;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemCat;
import com.welkin.pojo.TbItemCatExample;
import com.welkin.pojo.TbItemCatExample.Criteria;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemParam;
import com.welkin.pojo.TbItemParamItem;

@Service
public class TbItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	/**
	 * 插入商品信息
	 * @param po
	 * @param desc
	 * @param paramStr 当前商品规格信息
	 * @return
	 */
	public String save(TbItem po, String desc, String paramStr) {
		po.setId(System.currentTimeMillis());
		po.setStatus((byte) 1);
		po.setCreated(new Date());
		po.setUpdated(new Date());
		int x = tbItemMapper.insert(po);
		
		if(desc != null) {
			TbItemDesc tbItemDesc = new TbItemDesc();
			tbItemDesc.setItemId(po.getId());
			tbItemDesc.setItemDesc(desc);
			tbItemDesc.setCreated(new Date());
			tbItemDesc.setUpdated(new Date());
			tbItemDescMapper.insert(tbItemDesc);
		}
		if(paramStr != null) {
			TbItemParamItem tbItemParamItem = new TbItemParamItem();
			tbItemParamItem.setItemId(po.getId());
			tbItemParamItem.setParamData(paramStr);
			tbItemParamItem.setCreated(new Date());
			tbItemParamItem.setUpdated(new Date());
			tbItemParamItemMapper.insert(tbItemParamItem);
		}
		
		
		TMessage m = new TMessage();
		if(x > 0) {
			m.setStatus(200);
		} else {
			m.setStatus(500);
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
		List<TbItem> li = tbItemMapper.selectByExample(ex);
		// 获取查询集合
		// PageInfo<要查询的实体类> pi = new PageInfo<>(查询后的集合对象);
		PageInfo<TbItem> pi = new PageInfo<>(li);
		
		pager.setRows(li);
		pager.setTotal(pi.getTotal());
		return pager;
	}
	
	/**
	 * 功能：按父id查询当前类型的所有分类
	 * @param parent_id 父id
	 * @return 按父id查询当前类型的所有分类
	 */
	public List<TbItemCat> findByParentId(Long parent_id) {
		TbItemCatExample ex = new TbItemCatExample();
		// 根据查询对象模板生成条件对象
		Criteria c = ex.createCriteria();
		// 按父类id查询下级所有分类对象
		c.andParentIdEqualTo(parent_id);
		return tbItemCatMapper.selectByExample(ex);
	}
	
}
