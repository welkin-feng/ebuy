package com.welkin.middle.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatPojo {
	//在spring返回的json对象中属性名能够与客户端要求的u，n，i名称一致
	//@JsonProperty注解目标，在spring返回json字符串重新封装属性名称
	@JsonProperty(value="u")
	private String url;
	@JsonProperty(value="n")
	private String name;
	@SuppressWarnings("rawtypes")
	@JsonProperty(value="i")
	private List info;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@SuppressWarnings("rawtypes")
	public List getInfo() {
		return info;
	}
	@SuppressWarnings("rawtypes")
	public void setInfo(List info) {
		this.info = info;
	}
}
