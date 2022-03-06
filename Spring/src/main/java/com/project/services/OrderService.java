package com.project.services;

import com.project.model.customer.Order;

import java.util.List;

public interface OrderService {

  Order findOrderById(Long id);

  List<Order> findAll();

  Order addOrder(Order order, List<Long> products);

  void deleteById(Long id);
}
