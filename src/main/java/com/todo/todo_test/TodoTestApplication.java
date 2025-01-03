package com.todo.todo_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan // Only needed if you still have other @WebServlet annotations
@ImportResource({ "classpath*:applicationContext.xml" })
public class TodoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoTestApplication.class, args);
	}
}
