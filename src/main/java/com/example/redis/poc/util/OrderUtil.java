package com.example.redis.poc.util;

import com.example.redis.poc.model.Order;

public class OrderUtil {

	public static final Order getOrderFromLine(String line) {

		Order order = new Order();
		String[] orderAttributes = line.split(",");
		order.setRegion(orderAttributes[0]);
		order.setCountry(orderAttributes[1]);
		order.setItemType(orderAttributes[2]);
		order.setSalesChannel(orderAttributes[3]);
		order.setOrderPriority(orderAttributes[4]);
		order.setOrderDate(orderAttributes[5]);
		order.setOrderId(orderAttributes[6]);
		order.setShipDate(orderAttributes[7]);
		order.setUnitsSold(orderAttributes[8]);
		order.setUnitPrice(orderAttributes[9]);
		order.setUnitCost(orderAttributes[10]);
		order.setRevenew(orderAttributes[11]);
		order.setCost(orderAttributes[12]);
		order.setTotalProfit(orderAttributes[13]);
		return order;

	}
}
