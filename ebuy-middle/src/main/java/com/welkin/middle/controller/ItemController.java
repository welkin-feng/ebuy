package com.welkin.middle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/item")
public class ItemController {

	@RequestMapping(value = "")
	public String findItemTitle(String query, Integer page) {
		
		return null;
	}
}
