package com.csci5308.g17.user;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IUserCSVParser {
     boolean isCSVFormat(MultipartFile file);

     List<User> readCSV(InputStream is);
}
