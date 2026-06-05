package com.duoc.courseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.duoc.courseservice")
public class CourseserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseserviceApplication.class, args);
	}

}
