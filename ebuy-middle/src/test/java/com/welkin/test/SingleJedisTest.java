package com.welkin.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SingleJedisTest {

	private ApplicationContext applicationContext;

	@Test
	public void testJedisPool() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-jedis-single.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		jedis.set("name3", "lis3");
		String name = jedis.get("name3");
		System.out.println(name);

		jedis.close();
		pool.close();
	}

	@Test
	public void pool() {
		JedisPoolConfig config = new JedisPoolConfig();
		//最大连接数
		config.setMaxTotal(30);
		//最大连接空闲数
		config.setMaxIdle(2);

		@SuppressWarnings("resource")
		JedisPool pool = new JedisPool(config, "10.211.55.8", 6379);
		Jedis jedis = null;

		try  {
			jedis = pool.getResource();

			jedis.set("name", "lisi");
			String name = jedis.get("name");
			System.out.println(name);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(jedis != null){
				//关闭连接
				jedis.close();
			}
		}

	}

	// 连接redis集群
	@SuppressWarnings("resource")
	@Test
	public void cluster() {
		JedisPoolConfig config = new JedisPoolConfig();
		// 最大连接数
		config.setMaxTotal(30);
		// 最大连接空闲数
		config.setMaxIdle(2);

		//集群结点
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		jedisClusterNode.add(new HostAndPort("10.211.55.8", (Integer) 7001));
		jedisClusterNode.add(new HostAndPort("10.211.55.8", (Integer) 7002));
		jedisClusterNode.add(new HostAndPort("10.211.55.8", (Integer) 7003));
		jedisClusterNode.add(new HostAndPort("10.211.55.8", (Integer) 7004));
		jedisClusterNode.add(new HostAndPort("10.211.55.8", (Integer) 7005));
		jedisClusterNode.add(new HostAndPort("10.211.55.8", (Integer) 7006));
		//		JedisCluster jc = new JedisCluster(jedisClusterNode, config);

		JedisCluster jcd = new JedisCluster(jedisClusterNode);
		jcd.set("name", "zhangsan");
		String value = jcd.get("name");
		System.out.println(value);
	}
	
	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-jedis.xml");
	}

	//redis集群
	@Test
	public void testJedisCluster() {
		
		JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClientCluster");

		jedisCluster.set("name1", "zhangsan");
		String value = jedisCluster.get("name1");
		System.out.println(value);
	}

}
