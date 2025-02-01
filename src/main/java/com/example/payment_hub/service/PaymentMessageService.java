package com.example.payment_hub.service;

import com.example.payment_hub.model.PaymentMessage;
import com.example.payment_hub.repository.PaymentMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMessageService {
    private final PaymentMessageRepository repository;
    
    public PaymentMessage saveMessage(PaymentMessage message) {
        return repository.save(message);
    }
    
    public PaymentMessage findByMessageId(String messageId) {
        return repository.findByMessageId(messageId);
    }
}