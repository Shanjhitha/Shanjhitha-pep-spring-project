package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
        /* 
        if(registeredAccount != null){
            return new ResponseEntity<>(registeredAccount, HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
            */
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        return accountService.login(account);
        /* 
        if(loggedAccount != null){
            return new ResponseEntity<>(loggedAccount, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
            */
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId (@PathVariable Integer accountId){
        return new ResponseEntity<List<Message>>(messageService.getMessageByUser(accountId), HttpStatus.OK);
        /* 
        if(messagesById != null){
            return new ResponseEntity<> (HttpStatus.OK);
        } else{
            return new ResponseEntity<> (HttpStatus.NO_CONTENT);
        }
            */
    }
    
    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessages(@RequestBody Message message){
        return messageService.createMessage(message);
        //return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok().body(messageService.getAllMessages());
        /* 
        if(allMessages.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(allMessages, HttpStatus.OK);
        }
            */
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getSpecificMessage(@PathVariable Integer messageId){
        return ResponseEntity.ok().body(messageService.getMessageById(messageId));
        /* 
        Message specificMessage = messageService.getMessageById(messageId);
        if (specificMessage != null){
            return new ResponseEntity<>(specificMessage.getMessageText(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(specificMessage, HttpStatus.NOT_FOUND);
        }
            */
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId){
        Integer deletedRows = messageService.deleteMessageById(messageId);
        if (deletedRows == 1){
            return ResponseEntity.ok().body(1);
        } else{
            return ResponseEntity.ok().body(null);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message newMessage){
        Integer updatedRows = messageService.updateMessageById(messageId, newMessage);
        if(updatedRows == 1){
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }   
}
