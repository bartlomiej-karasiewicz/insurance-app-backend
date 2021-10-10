package com.task.backend.domain.model;

import com.sun.istack.NotNull;
import com.task.backend.infrastructure.reader.InsuranceReader.InsuranceRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "insurance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Insurance {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "insurance_number", nullable = false)
  @NotNull
  private Long insuranceNumber;

  @NotNull
  @Column(name = "policy_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private PolicyType policyType;

  @Column(name = "insured_sum", nullable = false)
  @NotNull
  private BigDecimal insuredSum;

  @Column(name = "insured_name", nullable = false)
  @NotNull
  private String insuredName;

  @Column(name = "insured_surname", nullable = false)
  @NotNull
  private String insuredSurname;

  @Column(name = "insured_item")
  private String insuredItem;

  public InsuranceRepresentation toRepresentation() {
    return new InsuranceRepresentation(id, insuranceNumber, policyType.getType(), insuredSum, insuredName, insuredSurname, insuredItem);
  }
}
