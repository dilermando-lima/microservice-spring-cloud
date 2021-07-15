package com.formcloud.formcreate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.formcloud" ) // inclunde com.formcloud.archcommons on start configuration 
public class MsFormCreateApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsFormCreateApplication.class, args);
	}

}
