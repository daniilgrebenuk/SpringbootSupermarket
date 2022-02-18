package com.project.services.implementation;

import com.project.model.customer.Customer;
import com.project.model.exception.DataNotFoundException;
import com.project.repository.customer.CustomerRepository;
import com.project.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;


  @Override
  public Customer add(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Customer findById(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new DataNotFoundException("Customer with current id doesn't exist: " + id));
  }

  @Override
  public Customer update(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public void delete(Customer customer) {
    customerRepository.delete(customer);
  }
}
