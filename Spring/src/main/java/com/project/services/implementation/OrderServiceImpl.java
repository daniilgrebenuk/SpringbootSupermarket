package com.project.services.implementation;

import com.project.model.customer.Order;
import com.project.model.product.Product;
import com.project.model.product.ProductOrder;
import com.project.repository.customer.OrderRepository;
import com.project.repository.product.ProductOrderRepository;
import com.project.services.CustomerService;
import com.project.services.OrderService;
import com.project.services.ProductService;
import com.project.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final ProductOrderRepository productOrderRepository;
  private final ProductService productService;
  private final CustomerService customerService;
  private final StorageService storageService;



  @Override
  public Order addOrder(Order o, List<Long> products) {

    Order order = orderRepository.save(o);

    Map<Long, Integer> amountOfProduct = new HashMap<>();
    products.forEach(p -> amountOfProduct.merge(p, 1, Integer::sum));

    amountOfProduct.forEach((k, a) -> {
      Product product = productService.findByProductId(k);

      ProductOrder productOrder = new ProductOrder();
      productOrder.setOrder(order);
      productOrder.setProduct(product);
      productOrder.setAmount(a);
      productOrderRepository.save(productOrder);
    });

    return order;
  }

  @Override
  public List<Order> findAll() {
    return orderRepository.findAll();
  }
}
