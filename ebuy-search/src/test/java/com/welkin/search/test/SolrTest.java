package com.welkin.search.test;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SolrTest {

	private ApplicationContext c;

	@Test
	public void solr1() {
		//直接指定solr的URL和core1，只能查询或更新core1内容
		SolrClient slorClient = new HttpSolrClient("http://10.211.55.9:8983/solr/collection1");
		
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "12");
		document.addField("item_title", "test");
		document.addField("item_sell_point", "test");
		document.addField("item_price", "12");
		document.addField("item_image", "test");
		document.addField("item_category_name", "test");


		try {
			slorClient.add(document);
			System.out.println("a");
			slorClient.commit();
			System.out.println("b");

			// 创建查询对象
			SolrQuery query = new SolrQuery();
			// 设置查询条件
			query.setQuery("id:12");
			// 根据查询条件查询索引库
			QueryResponse queryResponse = slorClient.query(query);
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			// 商品列表
			ArrayList<String> li = new ArrayList<>();
			for (SolrDocument solrDocument : solrDocumentList) {
				String e = (String) solrDocument.get("item_title");
				li.add(e);
			}
			System.out.println("count: " + solrDocumentList.getNumFound());
			for (String s : li) {
				System.out.println(s);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
