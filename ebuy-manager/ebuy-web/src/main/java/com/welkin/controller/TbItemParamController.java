package com.welkin.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welkin.pojo.TMessage;
import com.welkin.pojo.TbItemParam;
import com.welkin.service.TbItemParamService;

@Controller
@RequestMapping("/item/param")
public class TbItemParamController {
	@Autowired
	private TbItemParamService tbItemParamService;
	
	@RequestMapping("/query/itemcatid/{id}")
	@ResponseBody
	public TMessage find(@PathVariable Long id) {
		TbItemParam cat = tbItemParamService.findById(id);
		TMessage msg = new TMessage();
		msg.setStatus(200);
		if (cat != null) {
			msg.setData(cat);
		}
		return msg;
	}
	
	/**
	 * 功能：保存商品规格信息
	 * @param cid 对应商品类型的编号
	 * @param pstr 商品类型对应的商品规格基本信息
	 * @return
	 */
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TMessage save(@PathVariable Long cid, @RequestParam("paramData") String pstr) {
		// 封装产品规格数据
		TbItemParam po = new TbItemParam();
		// 设置对应产品类型的编号
		po.setId(cid);
		po.setParamData(pstr);
		po.setCreated(new Date());
		po.setUpdated(new Date());
		// 调用业务中保存方法
		boolean flag = tbItemParamService.save(po);
		TMessage msg = new TMessage();
		if(flag) 
			msg.setStatus(200);
		else
			msg.setStatus(500);
		return msg;
	}

}
