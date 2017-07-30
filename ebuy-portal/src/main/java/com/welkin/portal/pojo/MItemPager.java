package com.welkin.portal.pojo;

import java.io.Serializable;
import java.util.List;

import com.welkin.pojo.TbItem;

public class MItemPager implements Serializable{
	private Integer totalPages;
	private Integer page;
	private String query;
	private List<TbItem> itemList;
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<TbItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}
}
