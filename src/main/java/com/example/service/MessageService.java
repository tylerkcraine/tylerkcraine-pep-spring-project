package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService{
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MessageRepository messageRepository;

    /**
     * service method to add a message to the database
     * @param newMessage
     * @return Message object representing the now in database message
     */
    public Message addMessage(Message newMessage) {
        Account sender = accountRepository.findById(newMessage.getPosted_by()).orElseThrow(() -> new ClientErrorException("Message not created"));

        if (newMessage.getPosted_by() != sender.getAccount_id() || newMessage.getMessage_text().isBlank() || newMessage.getMessage_text().length() > 255) {
            throw new ClientErrorException("Message not created");
        }

        return messageRepository.saveAndFlush(newMessage);
    }


    /**
     * simple service method to return all the messages in the database
     * @return list of Message objects from the database
     */
    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    
}
