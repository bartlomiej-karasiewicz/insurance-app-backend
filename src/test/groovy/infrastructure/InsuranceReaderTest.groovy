package infrastructure

import com.task.backend.domain.model.Insurance
import com.task.backend.domain.model.PolicyType
import com.task.backend.infrastructure.InsuranceRepository
import com.task.backend.infrastructure.reader.InsuranceReader
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class InsuranceReaderTest extends Specification {

    InsuranceRepository insuranceRepository = Mock(InsuranceRepository.class);
    InsuranceReader insuranceReader = new InsuranceReader(insuranceRepository);

    def "should return paged insurances"() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<Insurance> insurances = new PageImpl<>(new ArrayList<>(Arrays.asList(
                new Insurance(0L, 111L, PolicyType.KOMUNIKACYJNA, BigDecimal.ONE, "Bartek", "Karasiewicz", "Ford Fiesta")
        )))
        given:
        insuranceRepository.findAll(pageRequest) >> insurances

        when:
        Page<InsuranceReader.InsuranceRepresentation> insuranceReader = insuranceReader.getAllInsurances(0, 1)

        then:
        insuranceReader.getTotalElements() == 1
        insuranceReader.content.get(0).getId() == 0L
        insuranceReader.content.get(0).getPolicyType() == 'Komunikacyjna'
    }
}
