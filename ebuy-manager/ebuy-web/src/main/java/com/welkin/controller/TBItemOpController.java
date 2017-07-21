package com.welkin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.TMessage;
import com.welkin.service.TBItemOpService;

@Controller
@RequestMapping("/rest/item")
public class TBItemOpController {
	@Autowired
	private TBItemOpService tBItemOpService;

	@RequestMapping("/delete")
	@ResponseBody
	public TMessage delete(@RequestParam(value = "ids", required = true) ArrayList<Long> ids) {
		
		TMessage msg = new TMessage();
		
		int x = tBItemOpService.delete(ids);
		
		if(x > 0) 
			msg.setStatus(200);
		else
			msg.setStatus(500);
		
		return msg;
		
	}
}
