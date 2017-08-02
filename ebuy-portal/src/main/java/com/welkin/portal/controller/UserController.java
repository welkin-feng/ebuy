package com.welkin.portal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.commons.CookieUtils;
import com.welkin.pojo.TbUser;
import com.welkin.portal.utils.HttpClientUtils;

@Controller
public class UserController {
	@Value("${MIDDLE_URL}")
	private String MIDDLE_URL;

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		return "index";
	}

	@RequestMapping("/userInfo")
	public ModelAndView showUserInfo(@RequestParam(value = "userName") String userName, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		// 检查cookie中是否包含登陆token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (null == token) {
			mv.setViewName("redirect:/");
			return mv;
		}

		// 访问middle服务器请求用户信息
		// 设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		// 发送请求 返回TbItem表的商品基本信息
		String url = "http://localhost:8005/userInfo";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		// 向指定url地址发送post请求
		String res = HttpClientUtils.doPost(url, params);
		System.out.println("res:" + res);

		// objectMapper
		ObjectMapper om = new ObjectMapper();
		if (res != "") {
			try {
				TbUser tbUser = om.readValue(res, TbUser.class);
				mv.addObject("tbUser", tbUser);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mv.setViewName("my-info");
			return mv;
		}
		mv.setViewName("redirect:/error/exception.html");
		return mv;
	}

	@RequestMapping("/imgInfo")
	public ModelAndView showUserImgInfo(@RequestParam(value = "userName") String userName, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		// 检查cookie中是否包含登陆token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (null == token) {
			mv.setViewName("redirect:/");
			return mv;
		}

		// 访问middle服务器请求用户信息
		// 设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		// 发送请求 返回TbItem表的商品基本信息
		String url = "http://localhost:8005/userInfo";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		// 向指定url地址发送post请求
		String res = HttpClientUtils.doPost(url, params);
		System.out.println("res:" + res);

		// objectMapper
		ObjectMapper om = new ObjectMapper();
		if (res != "") {
			try {
				TbUser tbUser = om.readValue(res, TbUser.class);
				mv.addObject("tbUser", tbUser);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mv.setViewName("my-info-img");
			return mv;
		}
		mv.setViewName("redirect:/error/exception.html");
		return mv;
	}

	@RequestMapping("/moreInfo")
	public ModelAndView showUserMoreInfo(@RequestParam(value = "userName") String userName, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		// 检查cookie中是否包含登陆token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (null == token) {
			mv.setViewName("redirect:/");
			return mv;
		}

		// 访问middle服务器请求用户信息
		// 设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		// 发送请求 返回TbItem表的商品基本信息
		String url = "http://localhost:8005/userInfo";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		// 向指定url地址发送post请求
		String res = HttpClientUtils.doPost(url, params);
		System.out.println("res:" + res);

		// objectMapper
		ObjectMapper om = new ObjectMapper();
		if (res != "") {
			try {
				TbUser tbUser = om.readValue(res, TbUser.class);
				mv.addObject("tbUser", tbUser);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mv.setViewName("my-info-more");
			return mv;
		}
		mv.setViewName("redirect:/error/exception.html");

		return mv;
	}
}
