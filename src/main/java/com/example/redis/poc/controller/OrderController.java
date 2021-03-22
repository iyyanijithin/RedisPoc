package com.example.redis.poc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.poc.model.FileLoad;
import com.example.redis.poc.model.Order;
import com.example.redis.poc.repo.OrderRepository;
import com.example.redis.poc.util.OrderUtil;

import redis.clients.jedis.Connection;

@RestController
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RedisTemplate<String, Object> template;

	@GetMapping(path = "/api/ping/redisserver")
	public String ping() {

		return template.getConnectionFactory().getConnection().ping();
	}

	@PostMapping(path = "/api/load/data")
	public String insertBulkOrder(@RequestBody FileLoad fileLoad) throws IOException {

		System.out.println("Started inserts");
		LineIterator it;
		boolean firstRow = true;
		long start = System.currentTimeMillis();

		it = FileUtils.lineIterator(new File(fileLoad.getFilePath()), "UTF-8");
		if (firstRow) {
			// header processing goes here
			it.nextLine();

		}
		List<Order> orders = new ArrayList<>();
		while (it.hasNext()) {
			String line = it.nextLine();
			Order order = OrderUtil.getOrderFromLine(line);
			orders.add(order);
			if (orders.size() == 1000) {
				List<Object> results = template.executePipelined(new RedisCallback<Object>() {
					public Object doInRedis(RedisConnection connection) throws DataAccessException {
						for (int i = 0; i < orders.size(); i++) {
							Map<byte[], byte[]> keyValuePair = orders.get(i).toMap();
							connection.hMSet((fileLoad.getLoadType() + ":" + orders.get(i).getOrderId()).getBytes(),
									keyValuePair);
							connection.sAdd(fileLoad.getLoadType().getBytes(), orders.get(i).getOrderId().getBytes());
						}
						return null;
					}
				});
				orders.clear();
			}
		}
		// inserting remaining orders
		if (!orders.isEmpty()) {
			List<Object> results = template.executePipelined(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					for (int i = 0; i < orders.size(); i++) {
						Map<byte[], byte[]> keyValuePair = orders.get(i).toMap();
						connection.hMSet((fileLoad.getLoadType() + ":" + orders.get(i).getOrderId()).getBytes(),
								keyValuePair);
						connection.sAdd(fileLoad.getLoadType().getBytes(), orders.get(i).getOrderId().getBytes());
					}
					return null;
				}
			});

		}

		// insert pending order

		System.out.println("Done inserts " + (System.currentTimeMillis() - start));
		return "Orders data inserted successfully in " + (System.currentTimeMillis() - start);
	}

	@GetMapping(path = "/api/risk/mismatch")
	public List<Order> keysInRiskNotInFiance() {
		List<Order> orders = new ArrayList<>();
		Set<byte[]> sDiff = template.getConnectionFactory().getConnection().sDiff("risk".getBytes(),
				"finance".getBytes());
		
		
		//We can add logic to do a seach for more generic search
		
		
		for (byte[] key : sDiff) {

			try (RedisConnection connection = template.getConnectionFactory().getConnection()) {

				Map<byte[], byte[]> hGetAll = connection.hGetAll(("risk:" + new String(key)).getBytes());
				if (!hGetAll.isEmpty()) {
					Order order = Order.getOrder(hGetAll);
					orders.add(order);
				}

			}

		}
		return orders;
	}

	@GetMapping(path = "/api/finance/mismatch")
	public List<Order> keysInFinanceNotInRisk() {
		List<Order> orders = new ArrayList<>();
		Set<byte[]> sDiff = template.getConnectionFactory().getConnection().sDiff("finance".getBytes(),
				"risk".getBytes());

		for (byte[] key : sDiff) {
			try (RedisConnection connection = template.getConnectionFactory().getConnection()) {
				Map<byte[], byte[]> hGetAll = connection.hGetAll(("finance:" + new String(key)).getBytes());
				if (!hGetAll.isEmpty()) {
					Order order = Order.getOrder(hGetAll);
					orders.add(order);
				}
			}
		}
		return orders;
	}
}
