package com.example.demo;

import com.example.demo.feign.AccountService;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

@EnableFeignClients(clients = {AccountService.class})
@SpringBootApplication
@RestController
public class FriendService {

	@GetMapping("/")
	String home() {
		return "Spring is here!";
	}

	public static void main(String[] args) {
		SpringApplication.run(FriendService.class, args);
	}
}