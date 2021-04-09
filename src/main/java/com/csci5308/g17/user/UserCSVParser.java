package com.csci5308.g17.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserCSVParser implements  IUserCSVParser {

    private final String CSV_MIME = "text/csv";
    private final String MS_EXCEL_MIME = "application/vnd.ms-excel";
    private static UserCSVParser instance;

    public static UserCSVParser getInstance(){
        if(instance == null){
            instance = new UserCSVParser();
        }
        return instance;
    }
    //https://www.pixeltrice.com/import-the-csv-file-into-mysql-database-using-spring-boot-application/
    @Override
    public boolean isCSVFormat(MultipartFile file) {
        if (file.getContentType().equals(CSV_MIME)
                || file.getContentType().equals(MS_EXCEL_MIME)) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> readCSV(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<User> userList = new ArrayList<>();

            Iterable<CSVRecord> userRecords = csvParser.getRecords();

            for (CSVRecord userRecord : userRecords) {
                User user = new User();
                user.setName(userRecord.get("name"));
                user.setEmail(userRecord.get("email"));
                user.setRole(userRecord.get("role"));
                user.setBannerId(userRecord.get("bannerId"));
                userList.add(user);
            }
            return userList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
