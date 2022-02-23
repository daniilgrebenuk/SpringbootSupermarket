package com.project.services;

import com.project.model.customer.Order;

import java.util.List;

public interface OrderService {

  Order addOrder(Order order, List<Long> products);

  List<Order> findAll();
}
