package com.welkin.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.welkin.portal.pojo.SearchResult;
import com.welkin.portal.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	/**
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String search(@RequestParam("q") String queryString,
			@RequestParam(value = "page", defaultValue = "1") Integer page, Model model)
			throws UnsupportedEncodingException {
		SearchResult searchResult = searchService.search(queryString, page);
		// 向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);

		return "search";
	}

	/*
	 * @RequestMapping(value = "/products/{categoryId}", produces =
	 * MediaType.APPLICATION_JSON_UTF8_VALUE) public String
	 * searchCategory(@PathVariable Long categoryId, Model model) { String
	 * queryString = ""; int page = 1; SearchResult searchResult =
	 * searchService.search(queryString, page); // 向页面传递参数
	 * model.addAttribute("query", queryString);
	 * model.addAttribute("totalPages", searchResult.getPageCount());
	 * model.addAttribute("itemList", searchResult.getItemList());
	 * model.addAttribute("page", page); return "search";
	 * 
	 * }
	 */

}
