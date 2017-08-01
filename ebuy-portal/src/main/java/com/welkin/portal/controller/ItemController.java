package com.welkin.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.commons.JsonUtils;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemParamItem;
import com.welkin.portal.pojo.TbItemInfo;
import com.welkin.portal.utils.HttpClientUtils;

@Controller
public class ItemController {
	@Value("${MIDDLE_URL}")
	private String MIDDLE_URL; // "http://localhost:8005"
	@Value("${MIDDLE_QUERY_ITEM_URL}")
	private String MIDDLE_QUERY_ITEM_URL; // "/item/query/item"
	@Value("${MIDDLE_QUERY_ITEMDESC_URL}")
	private String MIDDLE_QUERY_ITEMDESC_URL; // "/item/query/itemDesc"
	@Value("${MIDDLE_QUERY_ITEMPARAM_URL}")
	private String MIDDLE_QUERY_ITEMPARAM_URL; // "/item/query/itemParam"

	/**
	 * 根据itemId得到item的基本信息
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public ModelAndView getItem(@PathVariable Long itemId) {
		ModelAndView mv = new ModelAndView("item");

		// 发送请求 返回TbItem表的商品基本信息
		String url = MIDDLE_URL + MIDDLE_QUERY_ITEM_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		// 向指定url地址发送post请求
		String str = HttpClientUtils.doPost(url, params);
		System.out.println("str:" + str);

		// 将json的字符串转换成自定类
		TbItemInfo tbIteminfo = JsonUtils.jsonToObject(str, TbItemInfo.class);
		mv.addObject("item", tbIteminfo);

		return mv;
	}

	/**
	 * 根据itemId得到商品的详细介绍
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/item/desc/{itemId}")
	@ResponseBody
	public void getItemDesc(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		// 发送请求 返回TbItem表的商品基本信息
		String url = MIDDLE_URL + MIDDLE_QUERY_ITEMDESC_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		// 向指定url地址发送post请求
		String str = HttpClientUtils.doPost(url, params);
		System.out.println("str:" + str);

		// 将json的字符串转换成自定类
		TbItemDesc tbItemDesc = JsonUtils.jsonToObject(str, TbItemDesc.class);
		String res = tbItemDesc.getItemDesc();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.print(res);
			out.flush();
			out.close();
		}
	}

	/**
	 * 根据itemId得到商品的规格信息
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/item/param/{itemId}")
	@ResponseBody
	public void getItemParam(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		// 发送请求 返回TbItem表的商品基本信息
		String url = MIDDLE_URL + MIDDLE_QUERY_ITEMPARAM_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		// 向指定url地址发送post请求
		String str = HttpClientUtils.doPost(url, params);
		System.out.println("str:" + str);

		// 将json的字符串转换成自定类
		TbItemParamItem tbItemParamItem = JsonUtils.jsonToObject(str, TbItemParamItem.class);
		
		String res = tbItemParamItem.getParamData();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.print(res);
			out.flush();
			out.close();
		}

	}
}
