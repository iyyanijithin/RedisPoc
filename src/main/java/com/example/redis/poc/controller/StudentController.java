package com.example.redis.poc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.poc.model.Student;
import com.example.redis.poc.repo.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping(path = "/api/ping")
	public String helloWorld() {

		List<Student> studentList = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {

			studentList.add(new Student("Roger" + i, "roger" + i, Student.Gender.MALE, 1));
		}

		studentRepository.saveAll(studentList);
		return "Hello World";
	}
}
