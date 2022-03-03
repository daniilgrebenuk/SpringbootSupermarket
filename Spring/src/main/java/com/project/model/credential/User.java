package com.project.model.credential;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.customer.Customer;
import com.project.model.employee.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Table(name = "Users")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;
  private Boolean isNonExpired;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Role role;

  @OneToOne(mappedBy = "user")
  @JsonIgnore
  @ToString.Exclude
  private Employee employee;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  @ToString.Exclude
  private Customer customer;

  @PreRemove
  protected void preRemove(){
    if (employee != null)
      employee.setUser(null);
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthority().getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isNonExpired;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return isNonExpired;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    User user = (User) o;
    return id != null && Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
