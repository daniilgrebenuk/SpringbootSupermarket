package com.project.model.storage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.product.ProductSupply;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Supply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Storage storage;

  @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
  private LocalDate date;

  @OneToMany(mappedBy = "supplyProduct")
  private List<ProductSupply> products;

  @OneToMany(mappedBy = "supplyEmployee")
  private List<SupplyEmployee> employees;

  private boolean accepted;

  public Supply(Storage storage, LocalDate date) {
    this.storage = storage;
    this.date = date;
    this.accepted = false;
  }

  public void addProduct(ProductSupply productSupply){
    products.add(productSupply);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Supply supply = (Supply) o;
    return id != null && Objects.equals(id, supply.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}