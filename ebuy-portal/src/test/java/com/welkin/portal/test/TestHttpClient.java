package com.welkin.portal.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	//普通get请求
	@Test
	public void doGet() throws IOException{
		//1 创建客户端
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URI uri = null;
		CloseableHttpResponse response = null;

		try {
			URIBuilder ub = new URIBuilder("https://www.baidu.com/");
			// 添加参数
			ub.addParameter("wd", "a");
			uri = ub.build();
			//2创建Get
			HttpGet get=new HttpGet(uri);
			//3 通过客户端发送get请求，返回响应对象
			response = httpclient.execute(get);
			//4 获取响应对象中特性
			//得到服务器返回状态码
			int serverCode = response.getStatusLine().getStatusCode();
			System.out.println("服务器返回状态码:"+serverCode);
			//5得到服务器返回的响应内容
			HttpEntity entity = response.getEntity();
			//将响应的实体对象进行转码
			String out = EntityUtils.toString(entity, "UTF-8");
			System.out.println(out);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }

	}
}
