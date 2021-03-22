package com.example.redis.poc.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.redis.poc.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {
	
	
	
	
}