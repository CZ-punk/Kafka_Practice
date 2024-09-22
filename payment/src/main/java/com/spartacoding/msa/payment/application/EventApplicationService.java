package com.spartacoding.msa.payment.application;

import com.spartacoding.msa.payment.EventSerializer;
import com.spartacoding.msa.payment.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "Payment Event")
@RequiredArgsConstructor
public class EventApplicationService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentCompletedEvent(PaymentCompletedEvent event) {
        kafkaTemplate.send("payment-completed", EventSerializer.serialize(event));
    }
}
