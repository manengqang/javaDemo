package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public class FormSubmitDTO {
  private String username;
  private String age;
  MultipartFile file;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file; // 这里可以添加文件处理逻辑
  }
}
