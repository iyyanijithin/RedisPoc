package com.example.redis.poc.queue;

public interface MessagePublisher {

	void publish(final String message);
}