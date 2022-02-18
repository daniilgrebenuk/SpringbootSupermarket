package com.project.services;

import com.project.model.customer.Customer;

public interface CustomerService {

  Customer add(Customer customer);

  Customer findById(Long id);

  Customer update(Customer customer);

  void delete(Customer customer);
}
