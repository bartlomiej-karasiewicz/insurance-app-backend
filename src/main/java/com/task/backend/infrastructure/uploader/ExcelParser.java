package com.task.backend.infrastructure.uploader;

import com.task.backend.domain.model.Insurance;
import com.task.backend.domain.model.PolicyType;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class ExcelParser {

  private final MultipartFile file;

  public List<Insurance> parse() {
    try {
      Workbook workbook = new XSSFWorkbook(file.getInputStream());
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();
      List<Insurance> insurances = getInsurancesFromExcel(rowIterator);
      workbook.close();
      return insurances;
    } catch (IOException e) {
      throw new ExcelParserException(file.getName());
    }
  }

  private List<Insurance> getInsurancesFromExcel(Iterator<Row> rowIterator){
    List<Insurance> insurances = new ArrayList<>();
    int rowNumber = 0;
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      if (rowNumber == 0) {
        rowNumber++;
        continue;
      }
      Iterator<Cell> cellIterator = row.iterator();
      insurances.add(createInsurance(cellIterator));
    }
    return insurances;
  }

  private Insurance createInsurance(Iterator<Cell> cellIterator) {
    Insurance insurance = new Insurance();
    int indexCell = 0;
    while (cellIterator.hasNext()) {
      Cell cell = cellIterator.next();

      switch (indexCell) {

        case 0:
          insurance.setInsuranceNumber((long) cell.getNumericCellValue());
          break;

        case 1:
          insurance.setPolicyType(PolicyType.of(cell.getStringCellValue()));
          break;

        case 2:
          insurance.setInsuredSum(BigDecimal.valueOf(cell.getNumericCellValue()));
          break;

        case 3:
          insurance.setInsuredName(cell.getStringCellValue());
          break;

        case 4:
          insurance.setInsuredSurname(cell.getStringCellValue());
          break;

        case 5:
          insurance.setInsuredItem(cell.getStringCellValue());
          break;

        default:
          break;
      }
      indexCell++;
    }
    return insurance;
  }

  static class ExcelParserException extends RuntimeException {
    private static final long serialVersionUID = 8959978532653967522L;

    public ExcelParserException(String fileName) {
      super("Failed during parse file: " + fileName);
    }
  }
}
