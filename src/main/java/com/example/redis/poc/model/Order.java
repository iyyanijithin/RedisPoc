package com.example.redis.poc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Order")
public class Order {

	private String region;
	private String country;
	private String itemType;
	private String salesChannel;
	private String orderPriority;
	private String orderDate;
	@Id
	private String orderId;
	private String shipDate;
	private String unitCost;
	private String unitsSold;
	private String unitPrice;
	private String cost;
	private String revenew;
	private String totalCost;
	private String totalProfit;
	
	

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getOrderPriority() {
		return orderPriority;
	}

	public void setOrderPriority(String orderPriority) {
		this.orderPriority = orderPriority;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public String getUnitsSold() {
		return unitsSold;
	}

	public void setUnitsSold(String unitsSold) {
		this.unitsSold = unitsSold;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getRevenew() {
		return revenew;
	}

	public void setRevenew(String revenew) {
		this.revenew = revenew;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}

	public String getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	@Override
	public String toString() {
		return "Order [region=" + region + ", country=" + country + ", itemType=" + itemType + ", salesChannel="
				+ salesChannel + ", orderPriority=" + orderPriority + ", orderDate=" + orderDate + ", orderId="
				+ orderId + ", shipDate=" + shipDate + ", unitCost=" + unitCost + ", unitsSold=" + unitsSold
				+ ", unitPrice=" + unitPrice + ", cost=" + cost + ", revenew=" + revenew + ", totalCost=" + totalCost
				+ ", totalProfit=" + totalProfit + "]";
	}

	public Map<byte[], byte[]> toMap() {
		Map<byte[], byte[]> map = new HashMap<>();
		map.put("region".getBytes(), this.getRegion().getBytes());
		map.put("country".getBytes(), this.getCountry().getBytes());
		map.put("itemType".getBytes(), this.getItemType().getBytes());
		map.put("orderDate".getBytes(), this.getOrderDate().getBytes());
		map.put("orderId".getBytes(), this.getOrderId().getBytes());
		return map;
	}
	
	public static Order getOrder(Map<byte[], byte[]> input) {

		Order order = new Order();
		Set<Entry<byte[], byte[]>> entrySet = input.entrySet();
		for (Entry<byte[], byte[]> entry : entrySet) {

			String key = new String(entry.getKey());
			String value = new String(entry.getValue());
			if (key.equals("orderId")) {
				order.setOrderId(value);
			} else if (key.equals("country")) {
				order.setCountry(value);

			}
		}
		return order;

	}
}
