package com.task.backend.infrastructure.reader;

import com.task.backend.domain.Reader;
import com.task.backend.domain.model.Insurance;
import com.task.backend.infrastructure.InsuranceRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceReader implements Reader {

  private final InsuranceRepository insuranceRepository;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  public static class InsuranceRepresentation {
    private Long id;
    private Long insuranceNumber;
    private String policyType;
    private BigDecimal insuredSum;
    private String insuredName;
    private String insuredSurname;
    private String insuredItem;
  }

  @Override
  public Page<InsuranceRepresentation> getAllInsurances(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Insurance> insurances = insuranceRepository.findAll(pageRequest);

    List<InsuranceRepresentation> insuranceRepresentations = insurances.stream()
            .map(Insurance::toRepresentation)
            .collect(Collectors.toList());

    return new PageImpl<>(insuranceRepresentations, pageRequest, insurances.getTotalElements());
  }
}
