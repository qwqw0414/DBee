package com.joje.dbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling	// 스케줄링
//@EnableBatchProcessing	// 배치
@EnableAspectJAutoProxy	// AOP	
@ServletComponentScan
@SpringBootApplication
public class DBeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DBeeApplication.class, args);
	}

}
