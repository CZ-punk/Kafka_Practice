package com.spartacoding.msa.payment.domain;

import com.spartacoding.msa.payment.events.PaymentCompletedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment(Long orderId, BigDecimal amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public void complete() {
        status = PaymentStatus.COMPLETED;
    }

    public PaymentCompletedEvent createPaymentCompletedEvent() {
        return new PaymentCompletedEvent(id, orderId, status);
    }
}

