package com.project.model.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Discount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer discount;

  @ManyToOne
  private Category category;

  @Column(nullable = false)
  private LocalDate expireDate;

  private Boolean isGlobal;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Discount discount = (Discount) o;
    return id != null && Objects.equals(id, discount.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
