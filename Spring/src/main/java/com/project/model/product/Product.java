package com.project.model.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Category category;

  @OneToMany(mappedBy = "product")
  private List<ProductDiscount> productDiscounts;

  @OneToMany(mappedBy = "order")
  private List<ProductOrder> orders;




  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Product product = (Product) o;
    return id != null && Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "name = " + name + ", " +
        "category = " + category + ")";
  }
}
