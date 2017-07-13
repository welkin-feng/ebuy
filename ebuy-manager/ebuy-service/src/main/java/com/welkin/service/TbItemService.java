package com.welkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.welkin.mapper.TbItemMapper;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemExample.Criteria;

@Service
public class TbItemService {
	 @Autowired
	  private TbItemMapper tbItemdao;
	  
	//查询所有商品信息
	  public List<TbItem> findall(){
		  //mapping生成Example对象查询条件对象的模板
		  TbItemExample ex=new TbItemExample();
		  Criteria c = ex.createCriteria();//生成查询条件
		  c.andTitleLike("%电%");
		List<TbItem> li = tbItemdao.selectByExample(ex);
		return li;
	  }
	  
	  public Tpager findall(Integer page, Integer rows) {
			Tpager pager = new Tpager();

			TbItemExample ex = new TbItemExample();
			Criteria cri = ex.createCriteria();
			// 查询前开始分页
			PageHelper.startPage(page, rows);
			List<TbItem> li = tbItemdao.selectByExample(ex);
			PageInfo<TbItem> pi = new PageInfo<>(li);
			pager.setRows(li);
			pager.setTotal(pi.getTotal());
			return pager;
		}
}
