package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountExistsException;
import com.example.exception.ClientErrorException;
import com.example.exception.UnauthorizedException;
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
    MessageService messageService;

    //account paths start
    @PostMapping("register")
    public Account registerAccount(@RequestBody Account newAccount) {
        return accountService.addAccount(newAccount);
    }

    @PostMapping("login")
    public Account loginUser(@RequestBody Account loginAccount) {
        return accountService.login(loginAccount);
    }

    //message paths start
    @PostMapping("messages")
    public Message createMessage(@RequestBody Message newMessage) {
        return messageService.addMessage(newMessage);
    }

    @GetMapping("messages")
    public List<Message> getMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{a_id}")
    public Message getMessage(@PathVariable("a_id") Integer messageId) {
        return messageService.getMessage(messageId);
    }

    @GetMapping("accounts/{account_id}/messages")
    public List<Message> getMessagesFromAccount(@PathVariable("account_id") Integer accountId) {
        return messageService.getMessageFromAccountId(accountId);
    }

    @DeleteMapping("messages/{a_id}")
    public String deleteMessage(@PathVariable("a_id") Integer messageId) {
        Integer result = messageService.deleteMessage(messageId);
        if (result.equals(1)) {
            return "1";
        } else {
            return "";
        }
    }

    @PatchMapping("messages/{a_id}")
    public String editMessage(@PathVariable("a_id") Integer messageId, @RequestBody Message message) {
        messageService.editMessage(messageId, message);
        return "1";
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

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(UnauthorizedException e) {
        return e.getMessage();
    }
}
