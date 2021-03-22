package com.csci5308.g17.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CSVserviceTest {
    @Test
    void readCSV(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        CSVservice service=new CSVservice(userRepository);
        User dbUser = new User();
        dbUser.email="email";
        dbUser.name="user1";
        dbUser.password="password";
        dbUser.role="user";
        dbUser.bannerId="B00868907";
        List<User> user=new ArrayList<>();
        user.add(dbUser);
        Boolean check=true;
        Mockito.when(userRepository.saveALL(user)).thenReturn(user);
        Assertions.assertTrue(service.savetoDB(user), String.valueOf(check));

    }

}