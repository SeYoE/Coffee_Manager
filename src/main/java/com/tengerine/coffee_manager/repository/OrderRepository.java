package com.tengerine.coffee_manager.repository;

import com.tengerine.coffee_manager.model.Order;

public interface OrderRepository {
    Order insert(Order order);
}
