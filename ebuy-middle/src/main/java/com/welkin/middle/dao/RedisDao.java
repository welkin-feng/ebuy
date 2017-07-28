package com.welkin.middle.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

@Component
public class RedisDao {
	@Autowired
	private JedisCluster cluster;
	
	public Long delKey(String key) {
		return cluster.del(key);
	}
	
	public Long expireKey(String key, Integer sec) {
		return cluster.expire(key, sec);
	}
	public String setKey(String key, String value) {
		return cluster.set(key, value);
	}
	public String getKey(String key) {
		return cluster.get(key);
	}
	public Long hsetKey(String key, String field, String value) {
		return cluster.hset(key, field, value);
	}
	public String hgetKey(String key, String field) {
		return cluster.hget(key, field);
	}
	
}
