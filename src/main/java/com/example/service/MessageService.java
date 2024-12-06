package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // create new Messages
    public ResponseEntity<Message> createMessage(Message message){
        if (message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            return new ResponseEntity<Message> (HttpStatus.BAD_REQUEST);
        }
        if (accountRepository.findById(message.getPostedBy()).isEmpty()){
            return new ResponseEntity<Message> (HttpStatus.BAD_REQUEST);
        }
        Message newMessage = messageRepository.save(message);
        return new ResponseEntity<> (newMessage, HttpStatus.OK); 
    }  
    
    // Retrieves all messages
    public List<Message> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return messages; 
    }

    // Retrieves a specific message by its ID.
    public Message getMessageById(Integer messageId){
        Optional<Message> specificMessageById = messageRepository.findById(messageId); // why orElse imp 
        // asked to convert to Optional)
        
        if (specificMessageById.isPresent()){
            return specificMessageById.get();
        }

        return null;
    }

    // Retrieves all messages posted by a particular user.
    public List<Message> getMessageByUser(Integer accountId){
        List<Message> messagesByUser = messageRepository.findByPostedBy(accountId);
        return messagesByUser;
    }

    //Deletes a message by its ID.
    public Integer deleteMessageById(Integer messageId){
        Optional<Message> specificMessageById = messageRepository.findById(messageId); // why orElse imp 
        // asked to convert to Optional)
        
        if (specificMessageById.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }else{
            return 0;
        }

    }

    //Update a message by its ID
    public Integer updateMessageById(Integer messageId, Message newMessageText){
        Optional<Message> specificMessageById = messageRepository.findById(messageId); // why orElse imp 
        // asked to convert to Optional)
        
        if (!specificMessageById.isPresent() || newMessageText.getMessageText().length() > 255 || newMessageText.getMessageText().length() <= 0){
            return 0;
        }
        
        specificMessageById.get().setMessageText(newMessageText.getMessageText());
        messageRepository.save(specificMessageById.get());
        return 1; 
    }


}


