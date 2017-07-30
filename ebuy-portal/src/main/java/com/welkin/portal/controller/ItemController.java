package com.welkin.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.pojo.TbItemDesc;
import com.welkin.pojo.TbItemParamItem;
import com.welkin.portal.pojo.TbItemInfo;
import com.welkin.portal.utils.HttpClientUtils;

@Controller
public class ItemController {

	/**
	 * 根据itemId得到item的基本信息
	 * @param itemId
	 * @return 
	 */
	@RequestMapping("/{itemId}")
	public ModelAndView getItem(@PathVariable Long itemId) {
		ModelAndView mv = new ModelAndView("item");

		// 发送请求 返回TbItem表的商品基本信息
		String url = "http://localhost:8005/item/query/item";
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		// 向指定url地址发送post请求
		String str = HttpClientUtils.doPost(url, params);
		System.out.println("str:" + str);

		// json组件的转换器
		ObjectMapper om = new ObjectMapper();
		// 将json的字符串转换成自定类
		TbItemInfo tbIteminfo = null;

		try {
			tbIteminfo = om.readValue(str, TbItemInfo.class);
			mv.addObject("item", tbIteminfo);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mv;
	}

	/**
	 * 根据itemId得到商品的详细介绍
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/item/desc/{itemId}")
	@ResponseBody
	public void getItemDesc(@PathVariable Long itemId, 
			HttpServletRequest request, HttpServletResponse response) {
		//设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		// 发送请求 返回TbItem表的商品基本信息
		String url = "http://localhost:8005/item/query/itemDesc";
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		// 向指定url地址发送post请求
		String str = HttpClientUtils.doPost(url, params);
		System.out.println("str:" + str);
		
		// json组件的转换器
		ObjectMapper om = new ObjectMapper();
		// 将json的字符串转换成自定类
		TbItemDesc tbItemDesc = null;

		try {
			tbItemDesc = om.readValue(str, TbItemDesc.class);
			String res = tbItemDesc.getItemDesc();
			
			PrintWriter out;
			out = response.getWriter();
			out.print(res);
		    out.flush();
		    out.close();
		} catch (JsonParseException e) {
			
		} catch (JsonMappingException e) {
			
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * 根据itemId得到商品的规格信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/item/param/{itemId}")
	@ResponseBody
	public void getItemParam(@PathVariable Long itemId,
			HttpServletRequest request, HttpServletResponse response) {
		//设置编码格式
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
				
		// 发送请求 返回TbItem表的商品基本信息
		String url = "http://localhost:8005/item/query/itemParam";
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		// 向指定url地址发送post请求
		String str = HttpClientUtils.doPost(url, params);
		System.out.println("str:" + str);
		
		// json组件的转换器
		ObjectMapper om = new ObjectMapper();
		// 将json的字符串转换成自定类
		TbItemParamItem tbItemParamItem = null;

		try {
			tbItemParamItem = om.readValue(str, TbItemParamItem.class);
			String res = tbItemParamItem.getParamData();
			
			PrintWriter out;
			out = response.getWriter();
			out.print(res);
		    out.flush();
		    out.close();
		} catch (JsonParseException e) {
			
		} catch (JsonMappingException e) {
			
		} catch (IOException e) {
			
		}

	}
}
