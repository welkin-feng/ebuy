package com.welkin.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.search.pojo.SearchResult;
import com.welkin.search.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Message search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "60") Integer rows) {
		System.out.println("q = " + queryString + ", page = " + page + ", rows = " + rows);
		// 查询条件不能为空
		if (StringUtils.isBlank(queryString))
			return MessageUtil.build(400, "查询条件不能为空");
		SearchResult searchResult = null;
		try {
			queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
			searchResult = searchService.search(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.build(500, e.getMessage());
		}
		return MessageUtil.build(200, searchResult);
	}

}
