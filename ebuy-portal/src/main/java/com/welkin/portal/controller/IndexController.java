package com.welkin.portal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.welkin.portal.utils.HttpClientUtils;

@Controller
public class IndexController {
	@Value("${MIDDLE_URL}")
	private String MIDDLE_URL;
	@Value("${MIDDLE_ADLIST_URL}")
	private String MIDDLE_ADLIST_URL;
	
	@RequestMapping("/{pager}")
	public String gopager(@PathVariable String pager) {
		return pager;
	}

	@RequestMapping("/index")
	public String index(Model model) {
		System.out.println("首页信息");
		String url = MIDDLE_URL + MIDDLE_ADLIST_URL;

		String jsonArray = HttpClientUtils.doGet(url);
		// 将返回的json数组格式的字符串封装Model给 页面传值
		model.addAttribute("ad1", jsonArray);
		return "index";
	}

	/*@RequestMapping(value = "/itemcat/all", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String findAllCat(String callback, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// 设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		System.out.println("商品分类信息");
		String url = MIDDLE_URL + MIDDLE_ITEMCAT_URL;
		Map<String, String> param = new HashMap<>();
		param.put("callback", callback);
		String r = HttpClientUtils.doPost(url, param);

		System.out.println("return :" + r);
		return r;
	}*/
}
