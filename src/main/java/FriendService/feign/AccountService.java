package FriendService.feign;

import FriendService.feign.dtoauth.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("auth-service/api/v1/auth")
public interface AccountService {
    @PostMapping("/account")
    String postMewAccount(@RequestBody Account account);
}
