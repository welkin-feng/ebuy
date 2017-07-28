package com.welkin.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ItemController {
	
	/**
	 * 
	 * @param query 
	 * @param page
	 * @return
	 */
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam("q") String query, Integer page) {
		ModelAndView mv = new ModelAndView("search");
		try {
			query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.err.println("query: " + query);
		return mv;
	}

}
