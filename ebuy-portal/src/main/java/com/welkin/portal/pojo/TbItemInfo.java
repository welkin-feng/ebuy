package com.welkin.portal.pojo;

import com.welkin.pojo.TbItem;

public class TbItemInfo extends TbItem {
	public String[] getImages() {
		String image = getImage();
		if (image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
