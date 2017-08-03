package com.welkin.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welkin.commons.JsonUtils;
import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.mapper.TbOrderItemMapper;
import com.welkin.mapper.TbOrderMapper;
import com.welkin.pojo.Order;
import com.welkin.pojo.TbOrder;
import com.welkin.pojo.TbOrderExample;
import com.welkin.pojo.TbOrderExample.Criteria;
import com.welkin.pojo.TbOrderItem;
import com.welkin.pojo.TbOrderItemExample;
import com.welkin.portal.utils.HttpClientUtils;

@Service
public class OrderService {

	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;

	private String ORDER_URL = "http://localhost:8083";
	private String ORDER_CREATE_URL = "/create";

	public String createOrder(Order order) {
		
		String jsonStr = JsonUtils.objectToJson(order);
		Map<String, String> param = new HashMap<String, String>();
		param.put("jsonStr", jsonStr);
		// 调用order的服务提交订单。
		// 得到的 json 中包含状态码和订单 id
		String json = HttpClientUtils.doPost(ORDER_URL + ORDER_CREATE_URL, param);
		
		// 把 json 转换成 Message
		Message m = MessageUtil.jsonToMessage(json);
		if (m.getStatus() == 200) {
			Object orderId = m.getData();
			System.out.println("orderId: " + orderId.toString());
			return orderId.toString();
		}
		return "";
	}

	public List<TbOrder> getOrdersByUser(Long userId) {
		TbOrderExample ex = new TbOrderExample();
		Criteria c = ex.createCriteria();
		c.andUserIdEqualTo(userId);
		return tbOrderMapper.selectByExample(ex);

	}

	public List<TbOrderItem> getItemsByOrderId(String orderId) {
		TbOrderItemExample ex = new TbOrderItemExample();
		com.welkin.pojo.TbOrderItemExample.Criteria c = ex.createCriteria();
		c.andOrderIdEqualTo(orderId);
		return tbOrderItemMapper.selectByExample(ex);
	}
}
