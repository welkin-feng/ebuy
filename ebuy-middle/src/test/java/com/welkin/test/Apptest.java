package com.welkin.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.middle.service.ItemCatService;

public class Apptest {

	private static ClassPathXmlApplicationContext c;

	public static void main(String[] args) {
		c = new ClassPathXmlApplicationContext(new String[]{"spring/spring-dao.xml", "spring/spring-service.xml"});
		ItemCatService cs = c.getBean(ItemCatService.class);
		String cr = cs.getAllCat();
		ObjectMapper om = new ObjectMapper();
		try {
			String s = om.writeValueAsString(cr);
			System.out.println(s);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

}
