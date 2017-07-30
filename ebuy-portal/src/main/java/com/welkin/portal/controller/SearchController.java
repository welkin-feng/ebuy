package com.welkin.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.welkin.portal.pojo.SearchResult;

@Controller
public class SearchController {
	/**
	 * 
	 * @param query 
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/search")
	public String search(@RequestParam("q") String queryString, Integer page, Model model) throws UnsupportedEncodingException {
		if (queryString != null) {
			try {
				queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		SearchResult searchResult = searchService.search(queryString, page);
		//向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		
		return "search";
	}
}
