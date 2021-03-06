package com.project.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.credential.User;
import com.project.model.product.Discount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String mobileNumber;

  @OneToOne
  @JoinColumn(nullable = false)
  private User user;

  @ManyToMany
  @ToString.Exclude
  @JsonIgnore
  private List<Discount> discounts;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @ToString.Exclude
  @JsonIgnore
  private List<Order> orders;

  public void addDiscount(Discount discount) {
    discounts.add(discount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Customer customer = (Customer) o;
    return id != null && Objects.equals(id, customer.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
