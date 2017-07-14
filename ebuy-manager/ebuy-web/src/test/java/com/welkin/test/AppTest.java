package com.welkin.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.welkin.pojo.TPager;
import com.welkin.pojo.TbItem;
import com.welkin.service.TbItemService;

public class AppTest {
	public static void main(String[] args) {
		ApplicationContext c = new ClassPathXmlApplicationContext(new String[]{"spring/spring-dao.xml", "spring/spring-service.xml"});
		System.out.println(c);
		TbItemService service = c.getBean(TbItemService.class);
//		List<TbItem> list = service.findall();
		TPager pager = service.selectPager(3, 20);
		List<TbItem> list = (List<TbItem>) pager.getRows();
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());
		}
	}
	
}
