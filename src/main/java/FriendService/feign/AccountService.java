package FriendService.feign;

import FriendService.feign.dtoauth.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "AccountService", url = "http://localhost:8081/api/v1")
public interface AccountService {

    @PostMapping("/account")
    public String postMewAccount(@RequestBody Account account);
}
