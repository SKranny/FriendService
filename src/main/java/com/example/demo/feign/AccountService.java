package com.example.demo.feign;

import com.example.demo.feign.dtoauth.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "AccountService", url = "http://localhost:8081/api/v1/account/me")
public interface AccountService {

    @PutMapping("/account/me")
    public ResponseEntity EditAcc(@RequestBody Account account);
}
