package com.todo.todo_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TodoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoTestApplication.class, args);
	}
}
