package com.welkin.commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtil {
	// 定义jackson对象
	private static final ObjectMapper om = new ObjectMapper();

	/**
	 * 相当于 new Message(status, data);
	 * 
	 * @param status
	 * @param data
	 * @return
	 */
	public static Message build(Integer status, Object data) {
		Message m = new Message();
		m.setStatus(status);
		m.setData(data);
		return m;
	}

	/**
	 * 设置 status 的值为输入的参数值
	 * 
	 * @param status
	 * @return
	 */
	public static Message build(Integer status) {
		return build(status, null);
	}

	/**
	 * if x > 0 , status = 200; 
	 * else if x <= 0, status =500
	 * 
	 * @param x
	 * @return
	 */
	public static Message generateStatus(Integer x) {
		if (x > 0)
			return build(200);
		else
			return build(500);

	}

	/**
	 * flag == true, Message(200, true); 
	 * flag == false, Message(200, false)
	 * @param flag
	 * @return
	 */
	public static Message generateStatus(boolean flag) {
		if (flag)
			return build(200, true);
		else
			return build(500, false);
	}

	/**
	 * 将json结果集转化为 Message 对象，如果 clazz 参数不为 null，则设置 Massage.data 为这个类的对象
	 * @param <T>
	 * 
	 * @param jsonData
	 *            json数据
	 * @param clazz
	 *            Message 中的 Object data 的类型
	 * @return
	 */
	public static <T> Message jsonToMessage(String jsonData, Class<T> clazz) {

		try {
			if (clazz == null) {
				return JsonUtils.jsonToObject(jsonData, Message.class);
			}
			JsonNode jsonNode = om.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			T obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = om.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = om.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(), obj);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将Message类转化为json格式的String
	 * 
	 * @param m
	 * @return
	 */
	public static String messageToJson(Message m) {
		return JsonUtils.objectToJson(m);
	}

	

}
