package com.project.services;

import com.project.model.customer.Customer;

import java.util.List;

public interface CustomerService {

  List<Customer> findAll();

  Customer findById(Long id);

  Customer add(Customer customer);

  Customer update(Customer customer);

  void delete(Customer customer);

  Customer getCurrentCustomer();

  Customer addDiscountToCustomer(Long customerId, Long discountId);
}
