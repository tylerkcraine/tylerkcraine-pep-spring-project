package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.exception.AccountExistsException;
import com.example.exception.ClientErrorException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;

    @Autowired
    MessageService studentService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public Account registerAccount(@RequestBody Account newAccount) {
        return accountService.addAccount(newAccount);
    }


    //exceptions start
    @ExceptionHandler(AccountExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleExists(AccountExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBad(ClientErrorException e) {
        return e.getMessage();
    } 
}
