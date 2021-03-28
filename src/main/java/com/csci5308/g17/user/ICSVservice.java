package com.csci5308.g17.user;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ICSVservice {
     boolean hasCSVFormat(MultipartFile file);
     List<User> readCSV(InputStream is);
}
