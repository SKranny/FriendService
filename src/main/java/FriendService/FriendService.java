package FriendService;

import FriendService.feign.AccountService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

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