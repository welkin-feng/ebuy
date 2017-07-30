package com.welkin.portal.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.portal.pojo.SearchResult;
import com.welkin.portal.utils.HttpClientUtils;

@Service
public class SearchService {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	public SearchResult search(String queryString, int page) {
		System.out.println("search q = " + queryString);
		// 调用taotao-search的服务
		//查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page + "");
		try {
			//调用服务
			String json = HttpClientUtils.doGet(SEARCH_BASE_URL, param);
			System.out.println(json);
			//把字符串转换成java对象
			Message m = MessageUtil.formatToPojo(json, SearchResult.class);
			if (m.getStatus() == 200) {
				SearchResult result = (SearchResult) m.getData();
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
