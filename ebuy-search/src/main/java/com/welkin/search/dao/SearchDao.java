package com.welkin.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;
import com.welkin.search.pojo.Item;
import com.welkin.search.pojo.SearchResult;

@Component
public class SearchDao {
	@Resource(name = "httpSolrClient")
	private SolrClient solrClient;

	public SearchResult search(SolrQuery query) throws Exception {
		// 根据查询条件查询索引库
		QueryResponse queryResponse = solrClient.query(query);
		// 取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();

		// 返回值对象
		SearchResult result = new SearchResult();
		// 取查询结果总数量
		result.setRecordCount(solrDocumentList.getNumFound());

		// 商品列表
		List<Item> itemList = new ArrayList<>();
		// 取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		// 取商品列表
		for (SolrDocument solrDocument : solrDocumentList) {
			// 创建一商品对象
			Item item = new Item();
			item.setId((Long) solrDocument.get("id"));
			// 取高亮显示的结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			// 添加的商品列表
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;
	}

}
