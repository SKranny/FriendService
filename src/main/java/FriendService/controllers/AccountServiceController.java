package FriendService.controllers;

import FriendService.feign.AccountService;
import FriendService.feign.dtoauth.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AccountServiceController {

    private final AccountService accountService;

    @PostMapping ("/account/me")
    public String getAccount(@RequestBody Account account) {
        return accountService.postMewAccount(account).toString();
    }

}