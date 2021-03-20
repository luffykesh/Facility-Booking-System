//https://www.pixeltrice.com/import-the-csv-file-into-mysql-database-using-spring-boot-application/
package com.csci5308.g17.user;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVread {
    private UserRepository userRepo;
    public CSVread(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public static String fileType = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (fileType.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }

        return false;
    }

    public static List<User> readCSV(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<User> userList = new ArrayList<>();

            Iterable<CSVRecord> userRecords = csvParser.getRecords();

            for (CSVRecord userRecord : userRecords) {
                User user = new User();
                user.id=Integer.parseInt(userRecord.get("id"));
                user.name=userRecord.get("name");
                user.email=userRecord.get("email");
                user.password=userRecord.get("password");
                user.role=userRecord.get("role");
                user.bannerId=userRecord.get("bannerId");



                userList.add(user);
            }
            return userList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public void savetoDB(List<User> user) {
        userRepo.saveALL(user);


    }
    }

