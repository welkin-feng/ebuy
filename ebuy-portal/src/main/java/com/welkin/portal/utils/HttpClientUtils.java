package com.welkin.portal.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 * 模拟发送get请求
	 * @param url	get请求的url地址
	 * @param params	传递参数
	 * @return	服务器的响应数据
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String doGet(String url, Map<String, String> params) {
		CloseableHttpResponse response = null;
		String res = null;
		try {
			// 1 创建客户端
			CloseableHttpClient client = HttpClients.createDefault();
			// 2 创建URI对象
			URIBuilder uribuild = new URIBuilder(url);
			// 3 为get请求传递参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if(params != null && params.size() > 0) {
				for (Entry<String, String> en : params.entrySet()) {
					// 封装请求参数的对象
					BasicNameValuePair nv = new BasicNameValuePair(en.getKey(), en.getValue());
					list.add(nv);
				}
				// 在uriBuilder对象中添加指定参数
				uribuild.addParameters(list);
			}
			// 4 构建uri对象
			URI uri = uribuild.build();
			// 5 封装HttpGet对象
			HttpGet get = new HttpGet(uri);
			// 6 通过客户端发送get请求，返回响应对象
			response = client.execute(get);
			// 7 通过响应对象得到服务器状态
			int resCode = response.getStatusLine().getStatusCode();
			if(resCode != 200) 
				return "服务器返回错误信息......";
			// 8 得到响应对象中的实体
			HttpEntity entity = response.getEntity();
			// 9 将响应实体中的字符集进行处理
			res =  EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	public static String doPost(String url) {
		return doPost(url, null);

	}

	public static String doPost(String url, Map<String, String> params) {
		String res = null;
		CloseableHttpResponse response=null;
		try {
			// 1 创建客户端
			CloseableHttpClient client = HttpClients.createDefault();
			// 2 创建Uri对象
			URIBuilder uribuf = new URIBuilder(url);
			// 3为get请求传递参数
			// uribuf.addParameter(param, value)
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (params != null && params.size() > 0) {
				for (Entry<String, String> en : params.entrySet()) {
					// 封装请求参数的对象
					BasicNameValuePair nv = new BasicNameValuePair(en.getKey(), en.getValue());
					list.add(nv);
				}
			}
			// 4 模拟form表单封装
			UrlEncodedFormEntity form = new UrlEncodedFormEntity(list, "UTF-8");//
			// 5 通过uriBuilder对象构建一个uri
			URI uri = uribuf.build();
			// 6 创建Post对象
			HttpPost post = new HttpPost(uri);
			// 7 将表单对象封装到post对象中
			post.setEntity(form);

			// 8 发送post请求
			response = client.execute(post);

			// 9 通过响应对象得到服务器状态
			int resCode = response.getStatusLine().getStatusCode();
			if (resCode != 200)
				return "服务器返回错误信息...";

			// 10得到响应对象中实体
			HttpEntity entity = response.getEntity();
			// 11 将响应实体中的字符集进行处理
			res = EntityUtils.toString(entity, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
