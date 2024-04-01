package com.example.service;

import javax.persistence.EntityNotFoundException;

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

    public Message addMessage(Message newMessage) {
        Account sender = accountRepository.findById(newMessage.getPosted_by()).orElseThrow(() -> new ClientErrorException("Message not created"));

        if (newMessage.getPosted_by() != sender.getAccount_id() || newMessage.getMessage_text().isBlank() || newMessage.getMessage_text().length() > 255) {
            throw new ClientErrorException("Message not created");
        }

        return messageRepository.saveAndFlush(newMessage);
    }


}
