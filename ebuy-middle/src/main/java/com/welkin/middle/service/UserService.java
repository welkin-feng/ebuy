package com.welkin.middle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.welkin.commons.JsonUtils;
import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.dao.RedisDao;
import com.welkin.mapper.TbUserMapper;
import com.welkin.pojo.TbUser;
import com.welkin.pojo.TbUserExample;
import com.welkin.pojo.TbUserExample.Criteria;

@Service
public class UserService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private RedisDao redis;
	@Value("${TB_USER_KEY}")
	private String TB_USER_KEY;
	@Value("${USER_NAME_FIELD}")
	private String USER_NAME_FIELD;

	public Message findUserByUserName(String userName) {
		String value = redis.hget(TB_USER_KEY, USER_NAME_FIELD + userName);
		if (value != null && !value.equals("")) {
			return MessageUtil.build(200, value);
		}
		// 根据用户名查找用户
		TbUserExample ex = new TbUserExample();
		Criteria c = ex.createCriteria();
		c.andUsernameEqualTo(userName);
		List<TbUser> tbUsers = tbUserMapper.selectByExample(ex);

		// 返回查找到的用户
		if (tbUsers.size() == 0) {
			return MessageUtil.build(404, "未找到当前用户");
		} else if (tbUsers.size() > 1) {
			return MessageUtil.build(500, "找到多个当前用户");
		} else {
			value = JsonUtils.objectToJson(tbUsers.get(0));
			redis.hset(TB_USER_KEY, USER_NAME_FIELD + "", value);
			return MessageUtil.build(200, value);
		}
	}
}
