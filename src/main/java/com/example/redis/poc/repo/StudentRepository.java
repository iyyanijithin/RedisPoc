package com.example.redis.poc.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.redis.poc.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {
	
	
	
	
}