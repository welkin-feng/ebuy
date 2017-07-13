package com.welkin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PageUtils {
  @RequestMapping("/{pager}")
  public String execute(@PathVariable String pager){
	  System.out.println("pager 拦截："+pager);
	  return pager;
  }
  @RequestMapping("/xurl")
  public @ResponseBody User xxurl(Integer wid){
	  User u=new User();
	  u.setUid(88);
	  u.setUname("你好");
	  System.out.println("测试......"+wid);
	  return u;
  }
}
