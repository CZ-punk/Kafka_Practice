package com.spartacoding.msa.order.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    // TODO
    List<Order> findAll();
    Order save(Order order);

    Optional<Order> findById(Long id);
}
