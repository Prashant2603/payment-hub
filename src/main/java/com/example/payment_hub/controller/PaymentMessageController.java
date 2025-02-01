// src/main/java/com/example/paymenthub/controller/PaymentMessageController.java
package com.example.payment_hub.controller;

import com.example.payment_hub.model.PaymentMessage;
import com.example.payment_hub.service.PaymentMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class PaymentMessageController {
    private final PaymentMessageService service;
    
    @PostMapping
    public ResponseEntity<PaymentMessage> createMessage(@RequestBody PaymentMessage message) {
        return ResponseEntity.ok(service.saveMessage(message));
    }
    
    @GetMapping("/{messageId}")
    public ResponseEntity<PaymentMessage> getMessage(@PathVariable String messageId) {
        PaymentMessage message = service.findByMessageId(messageId);
        if (message != null) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }
}
