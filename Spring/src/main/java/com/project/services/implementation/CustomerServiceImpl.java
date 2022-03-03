package com.project.services.implementation;

import com.project.model.credential.User;
import com.project.model.customer.Customer;
import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Discount;
import com.project.repository.customer.CustomerRepository;
import com.project.services.CustomerService;
import com.project.services.DiscountService;
import com.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final UserService userService;
  private final DiscountService discountService;


  @Override
  public Customer getCurrentCustomer() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return customerRepository
        .findByUserUsername(username)
        .orElseThrow(()->new DataNotFoundException("Customer with current user.username doesn't exist: " + username));
  }

  @Override
  public Customer addDiscountToCustomer(Long customerId, Long discountId) {
    Customer customer = findById(customerId) ;
    Discount discount = discountService.findById(discountId);
    customer.addDiscount(discount);
    return customerRepository.save(customer);
  }

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  @Override
  public Customer add(Customer customer) {
    User user = userService.findUserById(customer.getUser().getId());
    customer.setUser(user);
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
    findById(customer.getId());
    return customerRepository.save(customer);
  }

  @Override
  public void deleteById(Long id) {
    customerRepository.deleteById(id);
  }
}
