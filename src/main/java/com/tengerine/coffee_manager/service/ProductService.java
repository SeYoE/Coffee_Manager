package com.tengerine.coffee_manager.service;

import com.tengerine.coffee_manager.model.Category;
import com.tengerine.coffee_manager.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByCategory(Category category);
    List<Product> getAllProducts();
    Product createProduct(String productName, Category category, long price);
    Product createProduct(String productName, Category category, long price, String description);
}
