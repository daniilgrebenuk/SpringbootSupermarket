package com.project.model.employee;

import com.project.model.credential.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String surname;
  private String age;
  private LocalDate enrollmentDate;
  private Integer contractInMonth;
  private Integer salary;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(nullable = false)
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Employee employee = (Employee) o;
    return id != null && Objects.equals(id, employee.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
