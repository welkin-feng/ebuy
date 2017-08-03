package com.welkin.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.welkin.commons.Message;
import com.welkin.portal.pojo.CartItem;
import com.welkin.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping("/cart")
	public ModelAndView showCart(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("cart");

		List<CartItem> cartList = cartService.getCartItemList(request);

		mv.addObject("cartList", cartList);

		return mv;
	}

	@RequestMapping("/add/{itemId}")
	public ModelAndView addItemToCart(@PathVariable Long itemId,
			@RequestParam(value = "number", defaultValue = "1") Integer number, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		System.out.println("itemId:" + itemId + ", number:" + number);

		Message mes = cartService.addCartItem(itemId, number, request, response);

		if (200 == mes.getStatus()) {
			mv.setViewName("cartSuccess");
			return mv;
		}
		return mv;
	}
	
	@RequestMapping("/update/num/{itemId}/{number}")
	public ModelAndView UpdateItemOfCart(@PathVariable Long itemId,
			@PathVariable Integer number,
			HttpServletRequest request,
			HttpServletResponse response) {
		List<CartItem> cartList = null;

		ModelAndView mv = new ModelAndView();
		System.out.println("update itemId:" + itemId + ", number:" + number);
		Message mes = cartService.updateCartItem(itemId, number, request, response);
		
		if (200 == mes.getStatus()) {
			mv.setViewName("redirect:/cart/cart.html");
			cartList = cartService.getCartItemList(request);
			mv.addObject("cartList", cartList);
			return mv;
		} else {
			mv.setViewName("/error/exception");
			return mv;
		}
	}

	@RequestMapping("/delete/{itemId}")
	public ModelAndView deleteItemFromCart(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		List<CartItem> cartList = null;

		ModelAndView mv = new ModelAndView();
		Message me = cartService.deleteCartItem(itemId, request, response);

		if (200 == me.getStatus()) {
			mv.setViewName("redirect:/cart/cart.html");
			cartList = cartService.getCartItemList(request);
			mv.addObject("cartList", cartList);
			return mv;
		} else if (404 == me.getStatus()) {
			mv.setViewName("/error/exception");
			return mv;
		} else if (500 == me.getStatus()) {
			mv.setViewName("/error/exception");
			return mv;
		} else {
			mv.setViewName("/error/exception");
			return mv;
		}
	}

	@RequestMapping("/deleteSelected")
	public ModelAndView deleteSelectedItemFromCart(@RequestParam(value = "cartList", defaultValue = "") String cartList,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println(cartList);
		ModelAndView mv = new ModelAndView();
		// 格式化cartList
		String[] _data = cartList.split(",");
		int num = _data.length;
		List<Long> itemIds = new ArrayList<Long>();
		for (int i = 0; i < num; i++) {
			itemIds.add(Long.valueOf(_data[i]));
		}
		// 删除所有被选中的商品
		Message me = cartService.deleteSelectedItem(itemIds, request, response);

		if (me.getStatus().equals(200)) {
			List<CartItem> _cartList = cartService.getCartItemList(request);
			mv.addObject("cartList", _cartList);
			mv.setViewName("redirect:/cart/cart.html");
			return mv;
		}

		mv.setViewName("/error/exception");
		return mv;
	}
}
