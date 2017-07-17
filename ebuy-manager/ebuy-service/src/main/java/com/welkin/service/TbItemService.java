package com.welkin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private TbItemCatMapper catMapper;
	
	//查询所有商品信息
	public List<TbItem> findall(){
		//mapping生成Example对象查询条件对象的模板
		TbItemExample ex=new TbItemExample();
		Criteria c = ex.createCriteria();//生成查询条件
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
	
	public List<Map<String, Object>> findItemCap(Long parent_id) {
		TbItemCatExample ex = new TbItemCatExample();
		// 根据查询对象模板生成条件对象
		com.welkin.pojo.TbItemCatExample.Criteria c = ex.createCriteria();
		// 按父类id查询下级所有分类对象
		c.andParentIdEqualTo(parent_id);
		List<TbItemCat> li = catMapper.selectByExample(ex);
		List<Map<String,Object>> mli=new ArrayList<Map<String,Object>>();
		//遍历分类集合
		for (TbItemCat cat :li) {
			//用于页面显示数据的封装  id,text,status
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("id",cat.getId());
			m.put("text",cat.getName());
			//封装当前节点状态
			m.put("state", cat.getIsParent()?"closed":"open");
			//将分类节点数据封装到集合中
			mli.add(m);
		}
		
		return mli;
		
	}
}
