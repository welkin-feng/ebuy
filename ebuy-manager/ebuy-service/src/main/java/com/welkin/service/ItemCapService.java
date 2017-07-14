package com.welkin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.mapper.TbItemCatMapper;
import com.welkin.pojo.TbItemCat;
import com.welkin.pojo.TbItemCatExample;
import com.welkin.pojo.TbItemCatExample.Criteria;

@Service
public class ItemCapService {
	@Autowired
	private TbItemCatMapper itemcatDao;

	/*public List<MTreeNode> findTrees(Long parentId){
		List<MTreeNode> mli=new ArrayList<MTreeNode>();
		TbItemCatExample ex=new TbItemCatExample();
		Criteria c = ex.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbItemCat> li = itemcatDao.selectByExample(ex);
		for (TbItemCat it : li) {
			MTreeNode tree=new MTreeNode();
			tree.setId(it.getId());
			tree.setText(it.getName());
			tree.setState(it.getIsParent()==true?"closed":"open");
			mli.add(tree);
		}
		return mli;
	}*/
}
