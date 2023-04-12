package FriendService;

import feignClient.EnableFeignClient;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import security.EnableMicroserviceSecurity;

@EnableFeignClient
@EnableEurekaClient
@SpringBootApplication
@EnableMicroserviceSecurity
public class FriendApp {
	public static void main(String[] args) {
		SpringApplication.run(FriendApp.class, args);
	}
}