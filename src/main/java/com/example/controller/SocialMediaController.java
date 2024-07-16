package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.entity.Message;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController

public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getOneMessageById(@PathVariable int message_id) {
        return messageService.getOneMessageById(message_id);
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int message_id) {
        return messageService.deleteMessage(message_id);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<String> updateMessage(@PathVariable int message_id, @RequestBody Map<String, String> requestBody) {
        String message_text = requestBody.get("messageText");
        return messageService.updateMessage(message_id, message_text);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getMessagesByAccountId(@PathVariable("account_id") Integer accountId) {
        return messageService.getMessageByAccountId(accountId);
    }
}

