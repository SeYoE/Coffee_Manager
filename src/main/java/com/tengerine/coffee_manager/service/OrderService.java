package com.tengerine.coffee_manager.service;

import com.tengerine.coffee_manager.model.Email;
import com.tengerine.coffee_manager.model.Order;
import com.tengerine.coffee_manager.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
