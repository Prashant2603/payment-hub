// src/main/java/com/example/paymenthub/controller/PaymentMessageController.java
package com.example.payment_hub.controller;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.payment_hub.model.PaymentMessage;
import com.example.payment_hub.service.PaymentMessageService;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class PaymentMessageController {
    private final PaymentMessageService service;
    
    private final MeterRegistry meterRegistry;

    @Value("${INSTANCE_ID:unknown}")
    private String instanceId;

    @PostMapping
    public ResponseEntity<PaymentMessage> createMessage(@RequestBody PaymentMessage message) {
        log.info("Instance {} processing create request for message: {}", instanceId, message.getMessageId());
        meterRegistry.counter("payment.requests.create", "instance", instanceId).increment();

        return ResponseEntity.ok(service.saveMessage(message));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<PaymentMessage> getMessage(@PathVariable String messageId) {
        log.info("Instance {} processing get request for message: {}", instanceId, messageId);
        meterRegistry.counter("payment.requests.get", "instance", instanceId).increment();
        PaymentMessage message = service.findByMessageId(messageId);
        return message != null ? ResponseEntity.ok(message) : ResponseEntity.notFound().build();
    }
}