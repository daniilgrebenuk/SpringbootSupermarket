package com.project.model.storage;

import com.project.model.employee.Employee;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SupplyEmployee {
  @EmbeddedId
  private SupplyEmployeeKey id;

  @ManyToOne
  @MapsId("employeeId")
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @ManyToOne
  @MapsId("supplyId")
  @JoinColumn(name = "supply_id")
  private Supply supplyEmployee;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    SupplyEmployee that = (SupplyEmployee) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
