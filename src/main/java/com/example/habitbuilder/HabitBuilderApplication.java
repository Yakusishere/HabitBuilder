package com.example.habitbuilder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//API-Key sk-c8493d735fcd488ba952e19e53138915
@SpringBootApplication
@MapperScan("com.example.habitbuilder.mapper")
public class HabitBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitBuilderApplication.class, args);
	}

}
