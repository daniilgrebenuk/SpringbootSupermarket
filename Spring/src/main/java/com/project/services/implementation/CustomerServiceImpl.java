package com.project.services.implementation;

import com.project.repository.customer.CustomerRepository;
import com.project.repository.customer.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl {
  private final CustomerRepository customerRepository;
  private final OrderRepository orderRepository;


}
