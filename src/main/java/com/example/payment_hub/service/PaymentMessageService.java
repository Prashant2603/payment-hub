package com.example.payment_hub.service;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.payment_hub.model.PaymentMessage;
import com.example.payment_hub.repository.PaymentMessageRepository;

@Service
@RequiredArgsConstructor
public class PaymentMessageService {
    private final PaymentMessageRepository repository;
    private final MeterRegistry meterRegistry;

    public PaymentMessage saveMessage(PaymentMessage message) {
        try {
            PaymentMessage saved = repository.save(message);
            // Count successful saves
            meterRegistry.counter("payment.messages.saved").increment();
            return saved;
        } catch (Exception e) {
            // Count failed saves
            meterRegistry.counter("payment.messages.save.error").increment();
            throw e;
        }
    }

    public PaymentMessage findByMessageId(String messageId) {
        meterRegistry.counter("payment.messages.retrieved").increment();
        return repository.findByMessageId(messageId);
    }
}