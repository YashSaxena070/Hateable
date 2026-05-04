package com.currix.distributed_lovable.account_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata")); // ← add this
        SpringApplication.run(AccountServiceApplication.class, args);
	}

}
