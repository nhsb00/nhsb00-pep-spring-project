package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Message> createMessage(Message message) {
        if (message.getMessageText().length() > 255 || message.getMessageText().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }        

        if (accountRepository.existsById(message.getPostedBy())) {
            Message newMessage = messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body(newMessage);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public List<Message> getAllMessages() {
        List<Message> allMessages = messageRepository.findAll();
        return allMessages;
    }

    public ResponseEntity<Message> getOneMessageById(int message_id) {
        Message messageById = messageRepository.findById(message_id).orElse(null);
        if (messageById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(messageById);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    public ResponseEntity<String> deleteMessage(int message_id) {
        if(messageRepository.existsById(message_id)) {
            messageRepository.deleteById(message_id);
            return ResponseEntity.status(HttpStatus.OK).body("1");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    public ResponseEntity<String> updateMessage(Integer message_id, String message_text) {
        if (message_text == null || message_text.isBlank() || message_text.length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message text");
        }
        if (!messageRepository.existsById(message_id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message not found");
        }
    
        Message messageToBeUpdated = messageRepository.findById(message_id).orElse(null);
        if(messageToBeUpdated != null) {
            messageToBeUpdated.setMessageText(message_text);
            messageRepository.save(messageToBeUpdated);
            return ResponseEntity.status(HttpStatus.OK).body("1");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message update failed");
        }
    }

    public List<Message> getMessageByAccountId(Integer accountId) {
        return messageRepository.findByAccountId(accountId); 
    }
}

