package com.task.backend.domain;

import com.task.backend.infrastructure.reader.InsuranceReader.InsuranceRepresentation;
import org.springframework.data.domain.Page;

public interface Reader {

  Page<InsuranceRepresentation> getAllInsurances(int page, int size);
}
