package com.atcorp.manageusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ManageusersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageusersApplication.class, args);
	}

}
