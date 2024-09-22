package com.spartacoding.msa.order.infrastructure.messaging;

import com.spartacoding.msa.order.EventSerializer;
import com.spartacoding.msa.order.application.OrderApplicationService;
import com.spartacoding.msa.order.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderApplicationService orderApplicationService;

    @KafkaListener(topics = "payment-completed", groupId = "order-group")
    public void handlePaymentCompletedEvent(String message) {
        PaymentCompletedEvent paymentEvent = EventSerializer.deserialize(message, PaymentCompletedEvent.class);
        orderApplicationService.completeOrder(paymentEvent.getOrderId());
    }

    // TODO order-msa가 구독하는 이벤트를 처리하는 로직을 개발해주세요
}
