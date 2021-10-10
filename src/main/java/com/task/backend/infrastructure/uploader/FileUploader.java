package com.task.backend.infrastructure.uploader;

import com.task.backend.domain.Uploader;
import com.task.backend.domain.model.Insurance;
import com.task.backend.infrastructure.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploader implements Uploader {

  private final InsuranceRepository insuranceRepository;

  @Transactional
  public void upload(MultipartFile file) {
    ExcelParser excelParser = new ExcelParser(file);
    insuranceRepository.saveAll(excelParser.parse());
    log.info("FILE PARSED: " + file.getOriginalFilename());
  }
}
