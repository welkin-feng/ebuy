package com.welkin.middle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.commons.JsonUtils;
import com.welkin.mapper.TbItemCatMapper;
import com.welkin.middle.dao.RedisDao;
import com.welkin.middle.pojo.CatPojo;
import com.welkin.middle.pojo.CatResult;
import com.welkin.pojo.TbItemCat;
import com.welkin.pojo.TbItemCatExample;
import com.welkin.pojo.TbItemCatExample.Criteria;

@Service
public class ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private RedisDao redis;

	public String getAllCat() {
		String value = redis.hgetKey("ItemCatService", "getAllCat");
		if (value != null && !value.equals("")) {
			System.out.println("redis hit!!!");
			return value;
		}
		System.out.println("redis does not hit!!!");
		CatResult res = new CatResult();
		// 为data属性赋值
		res.setData(getCatItems(0L));

		value = JsonUtils.objectToJson(res);
		redis.hsetKey("ItemCatService", "getAllCat", value);
		return value;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getCatItems(long pid) {
		// 返回的数据
		List rli = new ArrayList();
		// 按当前父层次id查询执行的子类型商品集合
		TbItemCatExample ex = new TbItemCatExample();
		// 创建查询条件对象
		Criteria c = ex.createCriteria();
		// 将父类型的id作为查询条件
		c.andParentIdEqualTo(pid);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(ex);
		// 遍历商品类型
		for (TbItemCat cate : list) {
			// 当前类型是否有子类型
			if (cate.getIsParent()) {
				CatPojo cp = new CatPojo();
				cp.setUrl("/product/" + cate.getId() + ".html");
				// 判断当前商品类型的parentId 是否为0
				if (cate.getParentId() == 0) {
					cp.setName("<a href='/products/" + cate.getId() + ".html'>" + cate.getName() + "</a>");
				} else {
					cp.setName(cate.getName());
				}
				// 封装 info 属性
				cp.setInfo(getCatItems(cate.getId()));
				// 封装了数据的对象存储在集合中
				rli.add(cp);
			} else {
				// 当前当前商品类型是没有子类型
				// "/products/3.html|电子书","/products/4.html|网络原创","/products/5.html|数字杂志","/products/6.html|多媒体图书"
				// 存储当前商品分类的信息
				rli.add("/products/" + cate.getId() + ".html|" + cate.getName());
			}
		}
		return rli;
	}

}
