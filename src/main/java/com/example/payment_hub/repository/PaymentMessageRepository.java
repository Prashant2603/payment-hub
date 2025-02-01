// src/main/java/com/example/paymenthub/repository/PaymentMessageRepository.java
package com.example.payment_hub.repository;

import com.example.payment_hub.model.PaymentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMessageRepository extends JpaRepository<PaymentMessage, Long> {
    PaymentMessage findByMessageId(String messageId);
}