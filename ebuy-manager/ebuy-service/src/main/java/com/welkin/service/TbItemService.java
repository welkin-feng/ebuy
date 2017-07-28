package com.welkin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.welkin.mapper.TbItemCatMapper;
import com.welkin.mapper.TbItemDescMapper;
import com.welkin.mapper.TbItemMapper;
import com.welkin.mapper.TbItemParamItemMapper;
import com.welkin.pojo.TbItem;
import com.welkin.pojo.TbItemCat;
import com.welkin.pojo.TbItemCatExample;
import com.welkin.pojo.TbItemCatExample.Criteria;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemExample;
import com.welkin.pojo.TbItemParamItem;
import com.welkin.pojo.TbItemParamItemExample;
import com.welkin.util.Pager;

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
	 * 根据itemid查询ItemDesc
	 * @param itemId
	 * @return
	 */
	public TbItemDesc findTbItemDescById(Long itemId) {
		TbItemDesc itemdesc = tbItemDescMapper.selectByPrimaryKey(itemId);

		return itemdesc;
	}

	/**
	 * 根据 商品 item_id 查询所对应的 规格参数信息
	 * @param paramid
	 * @return
	 */
	public TbItemParamItem findItemParamItemById(Long paramid) {
		TbItemParamItemExample ex = new TbItemParamItemExample();
		com.welkin.pojo.TbItemParamItemExample.Criteria c = ex.createCriteria();
		c.andItemIdEqualTo(paramid);
		List<TbItemParamItem> li = tbItemParamItemMapper.selectByExampleWithBLOBs(ex);

		if (li == null || li.size() < 1)
			return null;
		return li.get(0);
	}

	/**
	 * 插入商品信息
	 * @param po
	 * @param desc
	 * @param paramStr 当前商品规格信息
	 * @return
	 */
	public int save(TbItem po, String desc, String paramStr) {
		po.setId(System.currentTimeMillis());
		po.setStatus((byte) 1);
		po.setCreated(new Date());
		po.setUpdated(new Date());
		int x1 = tbItemMapper.insert(po);
		int x2 = 1, x3 = 1;

		if (desc != null) {
			TbItemDesc tbItemDesc = new TbItemDesc();
			tbItemDesc.setItemId(po.getId());
			tbItemDesc.setItemDesc(desc);
			tbItemDesc.setCreated(new Date());
			tbItemDesc.setUpdated(new Date());
			x2 = tbItemDescMapper.insert(tbItemDesc);
		}
		if (paramStr != null) {
			TbItemParamItem tbItemParamItem = new TbItemParamItem();
			tbItemParamItem.setItemId(po.getId());
			tbItemParamItem.setParamData(paramStr);
			tbItemParamItem.setCreated(new Date());
			tbItemParamItem.setUpdated(new Date());
			x3 = tbItemParamItemMapper.insert(tbItemParamItem);
		}

		return x1 & x2 & x3;
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

		TbItemExample ex = new TbItemExample();
		List<TbItem> li = tbItemMapper.selectByExample(ex);
		// 获取查询集合
		// PageInfo<要查询的实体类> pi = new PageInfo<>(查询后的集合对象);
		// PageInfo<TbItem> pi = new PageInfo<>(li);

		pager.setRows(li);
		// pager.setTotal(pi.getTotal());
		pager.setTotal((long) tbItemMapper.countByExample(ex));
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
