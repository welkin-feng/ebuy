package com.welkin.search.controller;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.search.service.ItemService;

@Controller
@RequestMapping("/manager")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/importall")
	@ResponseBody
	public Message importAll() {
		try {
			return itemService.importItemToIndex();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return MessageUtil.build(500, e.getMessage());
		}
	}

}
