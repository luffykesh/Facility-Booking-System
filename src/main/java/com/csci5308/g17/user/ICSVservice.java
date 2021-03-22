package com.csci5308.g17.user;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICSVservice {
    Boolean savetoDB(List<User> user);
}
