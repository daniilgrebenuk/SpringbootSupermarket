package com.project.model.storage;

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
@RequiredArgsConstructor
public class Supply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Storage storage;

  private LocalDate date;

  @OneToMany(mappedBy = "supplyProduct")
  @ToString.Exclude
  @JsonIgnore
  private List<ProductSupply> products;

  @OneToMany(mappedBy = "supplyEmployee")
  @ToString.Exclude
  @JsonIgnore
  private List<SupplyEmployee> employees;

  private boolean accepted;

  public void addProduct(ProductSupply productSupply){
    products.add(productSupply);
  }

  public void addAllProduct(Iterable<ProductSupply> productSupplies){
    productSupplies.forEach(products::add);
  }

  public void addEmployee(SupplyEmployee supplyEmployee) {
    employees.add(supplyEmployee);
  }

  public void addAllEmployee(Iterable<SupplyEmployee> supplyEmployees) {
    supplyEmployees.forEach(employees::add);
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