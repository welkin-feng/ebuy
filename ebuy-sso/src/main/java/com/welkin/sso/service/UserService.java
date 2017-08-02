package com.welkin.sso.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.welkin.commons.CookieUtils;
import com.welkin.commons.JsonUtils;
import com.welkin.commons.Message;
import com.welkin.commons.MessageUtil;
import com.welkin.dao.RedisDao;
import com.welkin.mapper.TbUserMapper;
import com.welkin.pojo.TbUser;
import com.welkin.pojo.TbUserExample;
import com.welkin.pojo.TbUserExample.Criteria;
import com.welkin.sso.service.UserService;

@Service
public class UserService {
	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private RedisDao redis;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE; // = 1800;

	public Message checkData(String content, Integer type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 对数据进行校验：1、2、3分别代表username、phone、email
		// 用户名校验
		if (1 == type)
			criteria.andUsernameEqualTo(content);
		// 电话校验
		else if (2 == type)
			criteria.andPhoneEqualTo(content);
		// email校验
		else
			criteria.andEmailEqualTo(content);

		// 执行查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return MessageUtil.generateStatus(true);
		return MessageUtil.generateStatus(false);
	}

	public Message createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		// md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		tbUserMapper.insert(user);
		return MessageUtil.generateStatus(true);
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Message userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		// 如果没有此用户名
		if (null == list || list.size() == 0)
			return MessageUtil.build(400, "用户名或密码错误");

		TbUser user = list.get(0);
		// 比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword()))
			return MessageUtil.build(400, "用户名或密码错误");

		// 生成token
		String token = UUID.randomUUID().toString();
		// 保存用户之前，把用户对象中的密码清空。
		user.setPassword(null);
		// 把用户信息写入redis
		redis.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		// 设置session的过期时间
		redis.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		// 添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效。
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		// 返回token
		return MessageUtil.generateStatus(true);
	}

	public Message getUserByToken(String token) {
		// 根据token从redis中查询用户信息
		String json = redis.get(REDIS_USER_SESSION_KEY + ":" + token);
		// 判断是否为空
		if (StringUtils.isBlank(json))
			return MessageUtil.build(400, "此session已经过期，请重新登录");
		// 更新过期时间
		redis.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		// 返回用户信息
		return MessageUtil.build(200, JsonUtils.jsonToObject(json, TbUser.class));
	}

}
