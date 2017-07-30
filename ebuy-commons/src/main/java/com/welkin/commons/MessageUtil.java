package com.welkin.commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtil {
	// 定义jackson对象
    private static final ObjectMapper om = new ObjectMapper();
	
	public static Message build(Integer status, Object data) {
		Message m = new Message();
		m.setStatus(status);
		m.setData(data);
		return m;
	}
	
	public static Message build(Integer status) {
		return build(status, null);
	}
	/**
	 * x > 0 , status = 200; x <= 0, status =500
	 * @param x
	 * @return
	 */
	public static Message generateStatus(Integer x) {
		if (x > 0)
			return build(200);
		else
			return build(500);
		
	}

	public static Message generateStatus(boolean flag) {
		if (flag)
			return generateStatus(1);
		else
			return generateStatus(0);
	}
	
	/**
     * 将json结果集转化为Message对象
     * 
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static Message formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return om.readValue(jsonData, Message.class);
            }
            JsonNode jsonNode = om.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
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
	
}
