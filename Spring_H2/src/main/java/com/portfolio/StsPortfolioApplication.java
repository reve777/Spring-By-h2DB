package com.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@ServletComponentScan
//@EnableScheduling
@EnableTransactionManagement
public class StsPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(StsPortfolioApplication.class, args);
	}

}
