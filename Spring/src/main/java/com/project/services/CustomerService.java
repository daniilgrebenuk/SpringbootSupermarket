package com.project.services;

import com.project.repository.customer.CustomerRepository;
import com.project.repository.customer.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final OrderRepository orderRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
    this.customerRepository = customerRepository;
    this.orderRepository = orderRepository;
  }
}
