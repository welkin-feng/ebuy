package com.welkin.sso.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.welkin.mapper.TbUserMapper;
import com.welkin.pojo.TbUser;
import com.welkin.pojo.TbUserExample;
import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;


@Service
public class UserService {

	@Autowired
	private TbUserMapper tbUserMapper;
	/*@Autowired
	private JedisCluster jedisCluster;*/
	
	/*@Value("${USER_TOKEN_KEY}")
	private String USER_TOKEN_KEY;
	@Value("${SESSION_EXPIRE_TIME}")
	private Integer SESSION_EXPIRE_TIME;*/
	
	public int doRegist(String username, String password, String phone) {
		
		TbUser record = new TbUser();
		record.setCreated(new Date());
		record.setUpdated(new Date());
		record.setUsername(username);
		record.setPassword(password);
		record.setPhone(phone);
		return tbUserMapper.insert(record);
		
	}

	public Message login(String username, String password) {

		//根据用户名查询用户信息
		TbUserExample ex = new TbUserExample();
		com.welkin.pojo.TbUserExample.Criteria criteria = ex.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(ex );
		if (null == list || list.isEmpty()) {
			return MessageUtil.build(400, "用户不存在");
		}
		//核对密码
		TbUser user = list.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return MessageUtil.build(400, "密码错误");
		}

		
		String token = UUID.randomUUID().toString();
//		System.out.println("token:" + token);
		
		Message m = MessageUtil.build(200);
		m.setData(token);
		
		return m;
	}
	
}
