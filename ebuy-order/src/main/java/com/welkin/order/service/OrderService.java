package com.welkin.order.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.dao.RedisDao;
import com.welkin.mapper.TbOrderItemMapper;
import com.welkin.mapper.TbOrderMapper;
import com.welkin.mapper.TbOrderShippingMapper;
import com.welkin.pojo.TbOrder;
import com.welkin.pojo.TbOrderItem;
import com.welkin.pojo.TbOrderShipping;

@Service
public class OrderService {
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private RedisDao redis;
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID; // = "100544";
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;
	@Value("${ORDER_DETAIL_INIT_ID}")
	private String ORDER_DETAIL_INIT_ID;
	

	/**
	 * 
	 * @param order
	 * @param itemList
	 * @param orderShipping
	 * @return 将订单 id 放入 Message 中返回
	 */
	public Message createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
		Date date = new Date();
		// 向订单表中插入记录
		// 获得订单号
		String string = redis.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(string)) {
			redis.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		long orderId = redis.incr(ORDER_GEN_KEY);
		// 补全pojo的属性
		order.setOrderId(orderId + "");
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		order.setStatus(1);
		
		order.setCreateTime(date);
		order.setUpdateTime(date);
		// 0：未评价 1：已评价
		order.setBuyerRate(0);
		// 向订单表插入数据
		orderMapper.insert(order);
		String orderInitId = redis.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(orderInitId)) {
			redis.set(ORDER_DETAIL_GEN_KEY, ORDER_DETAIL_INIT_ID);
		}
		// 插入订单明细
		for (TbOrderItem tbOrderItem : itemList) {
			// 补全订单明细
			// 取订单明细id
			long orderDetailId = redis.incr(ORDER_DETAIL_GEN_KEY);
			tbOrderItem.setId(orderDetailId + "");
			tbOrderItem.setOrderId(orderId + "");
			// 向订单明细插入记录
			int x1 = orderItemMapper.insert(tbOrderItem);
			if (x1 <= 0)
				return MessageUtil.generateStatus(false);
		}
		// 插入物流表
		// 补全物流表的属性
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		int x2 = orderShippingMapper.insert(orderShipping);
		if(x2 > 0)
			return MessageUtil.build(200, orderId);
		return MessageUtil.generateStatus(false);
	}
}
