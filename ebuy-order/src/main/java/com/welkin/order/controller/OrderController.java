package com.welkin.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.JsonUtils;
import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.order.service.OrderService;
import com.welkin.pojo.Order;

/**
 * 订单Controller
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/create")
	@ResponseBody
	//public Message createOrder(Order order) {
	public Message createOrder(String jsonStr) {
		
		try {
			Order order = JsonUtils.jsonToObject(jsonStr, Order.class);
			Message result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
			// 将订单 id 放入 Message 中返回
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.build(500, e.getMessage());
		}
	}
}
