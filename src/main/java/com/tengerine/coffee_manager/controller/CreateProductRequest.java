package com.tengerine.coffee_manager.controller;

import com.tengerine.coffee_manager.model.Category;

public record CreateProductRequest(
        String productName, Category category, long price, String description
) {
}
