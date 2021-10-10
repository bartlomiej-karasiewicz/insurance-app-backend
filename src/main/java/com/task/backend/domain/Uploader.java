package com.task.backend.domain;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {

  void upload(MultipartFile file);
}
