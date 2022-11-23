package com.example.demo.controllers;

import com.example.demo.feign.AccountService;
import com.example.demo.feign.dtoauth.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountServiceController {

    @Autowired
    private AccountService accountService;

    @PutMapping("/account/me")
    public ResponseEntity EditAcc(@RequestBody Account account) {
        //accountRepository.save(account);
       return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
