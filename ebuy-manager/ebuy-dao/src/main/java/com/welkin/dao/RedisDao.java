package com.welkin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

@Component
public class RedisDao {
	@Autowired
	private JedisCluster cluster;
	
	public Long del(String key) {
		return cluster.del(key);
	}
	
	public Long expire(String key, Integer sec) {
		return cluster.expire(key, sec);
	}
	public String set(String key, String value) {
		return cluster.set(key, value);
	}
	public String get(String key) {
		return cluster.get(key);
	}
	public Long hset(String key, String field, String value) {
		return cluster.hset(key, field, value);
	}
	public String hget(String key, String field) {
		return cluster.hget(key, field);
	}
	
	public Long incr(String key) {
		return cluster.incr(key);
	}
	
}
