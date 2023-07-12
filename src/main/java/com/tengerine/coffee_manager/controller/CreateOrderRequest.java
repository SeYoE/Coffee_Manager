package com.tengerine.coffee_manager.controller;

import com.tengerine.coffee_manager.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(
        String email, String address, String postcode, List<OrderItem> orderItems
) {
}
