package com.welkin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.welkin.mapper.TbItemCatMapper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.mapper.TbItemParamItemMapper;
import com.welkin.mapper.TbItemParamMapper;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemCat;
import com.welkin.pojo.TbItemCatExample;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemParam;
import com.welkin.pojo.TbItemParamExample;
import com.welkin.pojo.TbItemParamExample.Criteria;
import com.welkin.pojo.TbItemParamItem;
import com.welkin.pojo.TbItemParamItemExample;
import com.welkin.util.JsonUtil;
import com.welkin.util.Pager;

@Service
public class TbItemParamService {
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	/**
	 * 功能：更新产品类型规格基本信息，并同时更新该类型所有商品的规格信息
	 * @param tbItemParam
	 * @return
	 */
	public boolean update(TbItemParam tbItemParam) {
		tbItemParam.setUpdated(new Date());
		// 更新产品类型规格基本信息
		TbItemParamExample ex1 = new TbItemParamExample();
		Criteria c1 = ex1.createCriteria();
		c1.andItemCatIdEqualTo(tbItemParam.getItemCatId());
		int updateTbItemParamRes = tbItemParamMapper.updateByExampleSelective(tbItemParam, ex1);

		// 更新该类型所有商品的规格信息
		// 根据cid从Tb_Item中查询所有商品
		TbItemExample ex2 = new TbItemExample();
		com.welkin.pojo.TbItemExample.Criteria c2 = ex2.createCriteria();
		c2.andCidEqualTo(tbItemParam.getItemCatId());
		List<TbItem> tbItems = tbItemMapper.selectByExample(ex2);
		List<Long> tbItems_ItemId = new ArrayList<Long>();
		for (int i = 0; i < tbItems.size(); i++) {
			tbItems_ItemId.add(tbItems.get(i).getId());
		}
		// 根据所有商品id从Tb_Item_param_item查询所有商品规格信息
		TbItemParamItemExample ex3 = new TbItemParamItemExample();
		com.welkin.pojo.TbItemParamItemExample.Criteria c3 = ex3.createCriteria();
		c3.andItemIdIn(tbItems_ItemId);
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		int updateByTbItemParamItemRes = 0;
		for (int i = 0; i < tbItems_ItemId.size(); i++) {
			tbItemParamItem.setParamData(JsonUtil.JsonParamToString(tbItemParam.getParamData()));
			tbItemParamItem.setUpdated(new Date());
			updateByTbItemParamItemRes += tbItemParamItemMapper.updateByExampleSelective(tbItemParamItem, ex3);
		}
		if (updateTbItemParamRes > 0 && updateByTbItemParamItemRes > 0)
			return true;

		return false;
	}

	/**
	 * 删除“商品类别”对应的“规格参数”
	 * @param ids 要删除的规格参数item_param id List
	 * @return 删除状态
	 */
	public int delete(List<Long> ids) {
		TbItemParamExample ex = new TbItemParamExample();
		Criteria c = ex.createCriteria();
		c.andIdIn(ids);
		return tbItemParamMapper.deleteByExample(ex);
	}

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
	public int save(TbItemParam po) {
		po.setCreated(new Date());
		po.setUpdated(new Date());
		return tbItemParamMapper.insert(po);
	}

	/**
	 * 功能分页查询商品
	 * @param page 当前页码
	 * @param rows 每页显示记录数量
	 * @return 封装了分页信息的对象
	 */
	public Pager selectPager(Integer page, Integer rows) {
		Pager pager = new Pager();
		// 查询前开始分页
		// page: 第几页, rows：每页的记录数量
		PageHelper.startPage(page, rows);

		TbItemParamExample ex = new TbItemParamExample();
		List<TbItemParam> paramList = tbItemParamMapper.selectByExampleWithBLOBs(ex);

		List<Long> catIds = new ArrayList<>();
		for (int i = 0; i < paramList.size(); i++) {
			catIds.add(paramList.get(i).getItemCatId());
		}

		TbItemCatExample catEx = new TbItemCatExample();
		com.welkin.pojo.TbItemCatExample.Criteria c = catEx.createCriteria();
		c.andIdIn(catIds);
		List<TbItemCat> catList = tbItemCatMapper.selectByExample(catEx);

		List<TbItemParamDetail> tli = new ArrayList<>();
		for (int i = 0; i < catList.size(); i++) {
			TbItemParamDetail e = new TbItemParamDetail();
			e.setTbItemParam(paramList.get(i));
			e.setItemCatName(catList.get(i).getName());
			tli.add(e);
		}

		// 获取查询集合
		// PageInfo<要查询的实体类> pi = new PageInfo<>(查询后的集合对象);
		PageInfo<TbItemParamDetail> pi = new PageInfo<>(tli);

		pager.setRows(tli);
		pager.setTotal(pi.getTotal());
		return pager;
	}

}

class TbItemParamDetail {
	private TbItemParam tbItemParam;
	private Long id;
	private Long itemCatId;
	private Date created;
	private Date updated;
	private String paramData;
	private String itemCatName;

	public TbItemParam getTbItemParam() {
		return tbItemParam;
	}

	public void setTbItemParam(TbItemParam tbItemParam) {
		this.tbItemParam = tbItemParam;
		this.id = tbItemParam.getId();
		this.itemCatId = tbItemParam.getItemCatId();
		this.created = tbItemParam.getCreated();
		this.updated = tbItemParam.getUpdated();
		this.paramData = tbItemParam.getParamData();
	}

	public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}

	public Long getId() {
		return id;
	}

	public Long getItemCatId() {
		return itemCatId;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public String getParamData() {
		return paramData;
	}

}
