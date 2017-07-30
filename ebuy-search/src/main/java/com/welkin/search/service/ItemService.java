package com.welkin.search.service;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.search.dao.SearchDao;

@Service
public class ItemService {
	@Autowired
	private SearchDao searchDao;

	public Message importItemToIndex() throws IOException, SolrServerException {
		boolean status = searchDao.update();

		return MessageUtil.generateStatus(status);
	}

}
