package com.welkin.portal.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.welkin.commons.CookieUtils;
import com.welkin.commons.MessageUtil;
import com.welkin.pojo.TbOrder;
import com.welkin.pojo.TbUser;
import com.welkin.portal.pojo.CartItem;
import com.welkin.portal.pojo.Order;
import com.welkin.portal.service.CartService;
import com.welkin.portal.service.OrderService;
import com.welkin.portal.utils.HttpClientUtils;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	@Value("SSO_URL")
	private String SSO_URL;
	@Value("SSO_USER_TOKEN")
	private String SSO_USER_TOKEN;

	@RequestMapping("/myorders")
	public ModelAndView showMyorders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("my-orders");
		
		List<TbOrder> orderlist = orderService.getOrdersByUser(getTbUser(request).getId());
		/*List<TbOrderItem> itemlist = new ArrayList<TbOrderItem>();
		itemlist.clear();
		for (TbOrder tbOrder : orderlist) {
			itemlist = orderService.getItemsByOrderId(tbOrder.getOrderId());
		}*/
		
		mv.addObject("orderlist", orderlist);
		
		return mv;
	}
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		//取购物车商品列表
		List<CartItem> list = cartService.getCartItemList(request);
		//传递给页面
		model.addAttribute("cartList", list);
		
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(Order order, Model model, 
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			TbUser user = getTbUser(request);
			
			//在order对象中补全用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			//调用服务
			String orderId = orderService.createOrder(order);
			
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			
			//订单结算成功后将Cookie中购物车商品清空
			CookieUtils.deleteCookie(request, response, "TT_CART");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "您还未登录。请先返回首页登录！");
			return "error/exception";
		}
	}

	private TbUser getTbUser(HttpServletRequest request) {
		//从Request中取用户信息
		//TbUser user = (TbUser) request.getAttribute("user");
		
		//System.out.println("user:" + user);
		String userToken = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//System.out.println("userToken:" + userToken);
		
		String url = SSO_URL + SSO_USER_TOKEN + userToken;
		String tbUserJsonData = HttpClientUtils.doPost(url);
		
		TbUser user =  (TbUser) MessageUtil.jsonToMessage(tbUserJsonData, TbUser.class).getData();
		return user;
	}
	
}
