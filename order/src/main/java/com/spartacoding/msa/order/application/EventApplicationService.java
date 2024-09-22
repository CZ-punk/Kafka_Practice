package com.spartacoding.msa.order.application;

import com.spartacoding.msa.order.EventSerializer;
import com.spartacoding.msa.order.OrderTopic;
import com.spartacoding.msa.order.events.OrderCompletedEvent;
import com.spartacoding.msa.order.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventApplicationService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(OrderTopic.CREATED.getTopic(), EventSerializer.serialize(event));
    }

    public void publishOrderCompletedEvent(OrderCompletedEvent event) {
        kafkaTemplate.send(OrderTopic.COMPLETE.getTopic(), EventSerializer.serialize(event));
    }


}
