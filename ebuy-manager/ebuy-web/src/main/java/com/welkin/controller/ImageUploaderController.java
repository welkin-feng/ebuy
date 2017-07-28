package com.welkin.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welkin.service.ImageUploaderService;

@Controller
public class ImageUploaderController {
	@Autowired
	private ImageUploaderService uploader;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public String imgUpload(MultipartFile uploadFile) {

		System.out.println("upload:" + uploadFile);
		// 存储上传后的图片服务器上的路径
		StringBuilder url = new StringBuilder();
		Map<String, Serializable> m = new HashMap<String, Serializable>();

		boolean flag = uploader.upload(uploadFile, url);

		if (flag) {
			m.put("error", 0);
			m.put("url", url);
		} else {
			m.put("error", 1);
			m.put("message", "上传文件失败!");
		}

		// 将对象转换成字符串
		ObjectMapper om = new ObjectMapper();
		String s = null;
		try {
			s = om.writeValueAsString(m);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return s;

	}

}
