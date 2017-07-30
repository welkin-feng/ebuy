package com.welkin.search.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.search.mapper.ItemMapper;
import com.welkin.search.pojo.Item;

@Service
public class ItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrClient solrClient;
	
	public Message importItemToIndex() throws  IOException, SolrServerException {
		List<Item> list = itemMapper.getItemList();
		for (Item item : list) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			//将文档写入索引库
			solrClient.add(document);
		}
		//提交修改
		solrClient.commit();
		
		return MessageUtil.generateStatus(true);
	}


}
