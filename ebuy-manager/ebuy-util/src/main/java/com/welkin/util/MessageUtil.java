package com.welkin.util;

public class MessageUtil {
	public static Message generateStatus(int x) {
		Message m = new Message();
		if(x > 0) 
			m.setStatus(200);
		else
			m.setStatus(500);
		return m;
	}
}
