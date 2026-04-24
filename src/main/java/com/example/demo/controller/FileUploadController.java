package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Slf4j
@RestController
public class FileUploadController {

  @Autowired
  private UserService userService;

  @PostMapping("/excel/upload")
  public Result<String> uploadExcel(@RequestParam MultipartFile file) throws Exception {
    log.info("File upload file is {}", file.getOriginalFilename());
    
    try (InputStream inputStream = file.getInputStream()) {
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(0);
      
      for (Row row : sheet) {
        if (row.getRowNum() == 0) continue; // 跳过表头
        
        String username = row.getCell(0).getStringCellValue();
        int age = (int) row.getCell(1).getNumericCellValue();
        
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        log.info("User upload user is {}", user);
        userService.add(user);
      }
      
      workbook.close();
    }
    
    return Result.success("Excel文件上传并处理成功");
  }
}
