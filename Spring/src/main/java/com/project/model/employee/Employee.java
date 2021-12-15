package com.project.model.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.model.credential.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Enter name")
  private String name;

  @NotNull(message = "Enter surname")
  private String surname;

  private Long pesel;

  @NotNull(message = "Enter phone")
  private String phone;


  private String imageUrl;

  @NotNull(message = "Enter salary")
  private Integer salary;

  @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
  @NotNull(message = "Enter enrollment date")
  private LocalDate enrollmentDate;

  @NotNull(message = "Enter the duration of the contract in months")
  private Integer contractInMonth;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(nullable = false)
  @NotNull(message = "Enter userId and Role")
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
