package com.project.services;

import com.project.model.customer.Order;
import com.project.model.product.Product;

import java.util.List;
import java.util.Map;

public interface OrderService {

  Order addOrder(Order order, List<Long> products);

  List<Order> findAll();
}
