package com.example.habitbuilder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.habitbuilder.mapper")
public class HabitBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitBuilderApplication.class, args);
	}

}
