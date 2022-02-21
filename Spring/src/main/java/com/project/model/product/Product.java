package com.project.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotNull(message = "The product must have a name!")
  private String name;

  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull(message = "The product must have a category!")
  private Category category;


  @OneToMany(mappedBy = "order")
  @ToString.Exclude
  @JsonIgnore
  private List<ProductOrder> orders;

  @Column(nullable = false)
  @NotNull(message = "The product must have a price!")
  private Double price;


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
}
