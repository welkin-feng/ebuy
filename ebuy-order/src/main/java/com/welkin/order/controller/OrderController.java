package com.welkin.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.order.pojo.Order;
import com.welkin.order.service.OrderService;

/**
 * 订单Controller
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Message createOrder(@RequestBody Order order) {
		try {
			Message result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.build(500, e.getMessage());
		}
	}
}
