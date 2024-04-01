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
        if (!messageRepository.existsById(newMessage.getPosted_by()) || 
            newMessage.getMessage_text().isBlank() || 
            newMessage.getMessage_text().length() > 255) {
            throw new ClientErrorException("Message not created");
        }

        return messageRepository.saveAndFlush(newMessage);
    }


    /**
     * simple service method to return all the messages in the database
     * @return list of Message objects from the database
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * simple service method to return a message from its' id
     * @param messageId
     * @return the message retrieved from the database, null if not found
     */
    public Message getMessage(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    /**
     * simple method to get all the messages posted by a certain account
     * @param accountId
     * @return list of messages retrieved from database, empty if none found
     */
    public List<Message> getMessageFromAccountId(Integer accountId) {
        return messageRepository.findMessageByPostedBy(accountId);
    }

    /**
     * simple method to delete a message from the database based on id
     * @param messageId
     * @return 1 if successful 0 if not
     */
    public Integer deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        } else {
            return 0;
        }
    }
}
