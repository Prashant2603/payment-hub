package com.example.payment_hub.service;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.example.payment_hub.model.PaymentMessage;
import com.example.payment_hub.repository.PaymentMessageRepository;
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentMessageService {
    private final PaymentMessageRepository repository;
    private final MeterRegistry meterRegistry;

    public PaymentMessage saveMessage(PaymentMessage message) {
        try {
            log.info("Saving payment message with ID: {}", message.getMessageId());
            PaymentMessage saved = repository.save(message);
            meterRegistry.counter("payment.messages.saved").increment();
            log.info("Successfully saved payment message with ID: {}", saved.getMessageId());
            return saved;
        } catch (Exception e) {
            log.error("Error saving payment message: {}", e.getMessage(), e);
            meterRegistry.counter("payment.messages.save.error").increment();
            throw e;
        }
    }

    public PaymentMessage findByMessageId(String messageId) {
        log.info("Searching for payment message with ID: {}", messageId);
        PaymentMessage message = repository.findByMessageId(messageId);
        if (message == null) {
            log.warn("Payment message not found for ID: {}", messageId);
        } else {
            log.info("Found payment message with ID: {}", messageId);
        }
        return message;
    }
}