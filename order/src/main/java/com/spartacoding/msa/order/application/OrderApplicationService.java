package com.spartacoding.msa.order.application;

import com.spartacoding.msa.order.domain.Order;
import com.spartacoding.msa.order.domain.OrderRepository;
import com.spartacoding.msa.order.dto.OrderCreateDto;
import com.spartacoding.msa.order.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {
    private final OrderRepository orderRepository;
    private final EventApplicationService eventApplicationService;

    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto orderCreateDto) {

        Order order = new Order(orderCreateDto.getProductId(), orderCreateDto.getQuantity(), orderCreateDto.getCustomerEmail(), orderCreateDto.getTotalPrice());
        orderRepository.save(order);

        // TODO 주문 생성에 필요한 로직과 이벤트를 발행해 주세요
        eventApplicationService.publishOrderCreatedEvent(order.createOrderCreatedEvent());
        return convertToDto(order);
    }

    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Not Found Order"));
        order.complete();
        orderRepository.save(order);

        eventApplicationService.publishOrderCompletedEvent(order.createOrderCompletedEvent());
        // TODO 주문이 성공적으로 완료된 로직과, 이벤트를 발행해 주세요
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto convertToDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCustomerEmail()
        );
    }
}
