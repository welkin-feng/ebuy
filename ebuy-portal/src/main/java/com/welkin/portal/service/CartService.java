package com.welkin.portal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.commons.CookieUtils;
import com.welkin.commons.JsonUtils;
import com.welkin.commons.Message;
import com.welkin.pojo.TbItem;
import com.welkin.portal.pojo.CartItem;
import com.welkin.portal.utils.HttpClientUtils;

@Service
public class CartService {
	@Value("${MIDDLE_URL}")
	private String MIDDLE_URL;
	@Value("${MIDDLE_QUERY_ITEM_URL}")
	private String MIDDLE_QUERY_ITEM_URL;	// "/item/query/item"

	/**
	 * 从cookie中取商品列表
	 * 
	 * @return
	 */
	public List<CartItem> getCartItemList(HttpServletRequest request) {
		// 从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (cartJson == null) {
			return new ArrayList<>();
		}
		// 把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/**
	 * 向购物车中添加商品，默认添加到cookie中
	 * 
	 * @param itemId
	 *            商品Id
	 * @param number
	 *            商品数量
	 * @param request
	 * @param response
	 * @return
	 */
	public Message addCartItem(Long itemId, Integer number, HttpServletRequest request, HttpServletResponse response) {
		Message me = new Message();

		// 取商品信息
		CartItem cartItem = null;
		// 取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for (CartItem item : itemList) {
			// 如果存在此商品
			if (item.getId().equals(itemId)) {
				// 增加商品数量
				item.setNum(item.getNum() + number);
				cartItem = item;
				break;
			}
		}
		// 购物车中不存在该商品
		if (cartItem == null) {
			System.out.println("购物车中不存在该商品");

			// 查找商品信息
			Map<String, String> params = new HashMap<String, String>();
			params.put("itemId", itemId + "");
			String json = HttpClientUtils.doPost(MIDDLE_URL + MIDDLE_QUERY_ITEM_URL, params);
			// System.out.println("json:"+json);
			TbItem item = null;
			ObjectMapper om = new ObjectMapper();
			try {
				item = om.readValue(json, TbItem.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 为购物车中商品对象赋值
			cartItem = new CartItem();
			cartItem.setId(itemId);
			cartItem.setTitle(item.getTitle());
			cartItem.setNum(number);
			cartItem.setPrice(item.getPrice());
			cartItem.setImages(item.getImage() == null ? null : item.getImage().split(","));

			// 添加到购物车列表
			itemList.add(cartItem);
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		me.setStatus(200);
		System.out.println("购物车添加商品/itemId:" + cartItem.getId() + ",number:" + cartItem.getNum());

		return me;
	}

	/**
	 * 从购物车中删除指定商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	public Message deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		Message me = new Message();
		//获取当前cookie中的购物车的所有商品信息
		List<CartItem> itemList = getCartItemList(request);

		CartItem cartItem = null;
		// 判断购物车商品列表中是否存在此商品
		for (CartItem item : itemList) {
			// 找到该商品
			if (item.getId().equals(itemId)) {
				cartItem = item;
				break;
			}
		}
		if(cartItem == null) {
			//未找到该商品
			System.out.println("购物车中没有"+itemId+"商品");
			//更新购物车列表，并重新写入cookie
			CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
			me.setStatus(404);
			return me;
		}
		else {
			//找到该商品
			boolean flag = itemList.remove(cartItem);
			if(flag) {
				System.out.println("购物车删除"+itemId+"商品成功");
				//更新购物车列表，并重新写入cookie
				CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
				me.setStatus(200);
				return me;
			}
			else {
				System.out.println("购物车删除"+itemId+"商品失败");
				//更新购物车列表，并重新写入cookie
				CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
				me.setStatus(500);
				return me;
			}
		}
	}
}
