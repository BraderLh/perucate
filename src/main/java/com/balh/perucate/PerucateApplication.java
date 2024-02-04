package com.balh.perucate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients("com.balh.perucate")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class PerucateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerucateApplication.class, args);
	}

}
