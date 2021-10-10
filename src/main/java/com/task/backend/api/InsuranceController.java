package com.task.backend.api;

import com.task.backend.domain.Reader;
import com.task.backend.infrastructure.uploader.FileUploader;
import com.task.backend.infrastructure.reader.InsuranceReader.InsuranceRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@CrossOrigin("https://insurance-application-frontend.herokuapp.com/")
@RequestMapping("/api")
@RequiredArgsConstructor
public class InsuranceController {

  public static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  private final Reader reader;
  private final FileUploader fileUploader;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  static class ResponseMessage {
    private String message;
  }


  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String msg = "";
    if (Objects.equals(file.getContentType(), CONTENT_TYPE)) {
      try {
        msg = "File parsed";
        fileUploader.upload(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(msg));
      } catch (Exception e) {
        msg = "Problem with file parsing";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(msg));
      }
    }
    msg = "There is not an excel file.";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(msg));
  }

  @GetMapping("/insurances")
  public ResponseEntity<Page<InsuranceRepresentation>> getAllInsurances(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
    try {
      Page<InsuranceRepresentation> insurances = reader.getAllInsurances(page, size);
      return new ResponseEntity<>(insurances, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
