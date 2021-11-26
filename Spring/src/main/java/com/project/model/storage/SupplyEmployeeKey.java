package com.project.model.storage;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class SupplyEmployeeKey implements Serializable {

  @Column(name = "supply_Id")
  private Long supplyId;
  @Column(name = "employee_Id")
  private Long employeeId;
}
