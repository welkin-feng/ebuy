package com.welkin.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.pojo.CateResult;
import com.welkin.service.ItemCatService;

public class Apptest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext(new String[]{"spring/spring-dao.xml", "spring/spring-service.xml"});
		ItemCatService cs = c.getBean(ItemCatService.class);
		CateResult cr = cs.getAllCate();
		ObjectMapper om = new ObjectMapper();
		try {
			String s = om.writeValueAsString(cr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

}
