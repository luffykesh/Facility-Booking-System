package com.csci5308.g17.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void getIdTest() {
        User user = new User();
        final Integer ID = 100;

        Assertions.assertNull(user.getId());

        user.setId(ID);
        Assertions.assertEquals(ID, user.getId());
    }

    @Test
    public void setIdTest() {

        User user = new User();
        final Integer ID = 100;

        user.setId(ID);
        Assertions.assertEquals(ID, user.getId());
    }

    @Test
    public void getNameTest() {
        User user = new User();
        final String NAME = "Jane Doe";

        Assertions.assertNull(user.getName());

        user.setName(NAME);
        Assertions.assertEquals(NAME, user.getName());
    }

    @Test
    public void setNameTest() {
        User user = new User();
        final String NAME = "Jane Doe";

        user.setName(NAME);
        Assertions.assertEquals(NAME, user.getName());
    }

    @Test
    public void getEmailTest() {
        User user = new User();
        final String EMAIL = "email@host.com";

        Assertions.assertNull(user.getEmail());

        user.setEmail(EMAIL);
        Assertions.assertEquals(EMAIL, user.getEmail());
    }

    @Test
    public void setEmailTest() {
        User user = new User();
        final String EMAIL = "email@host.com";

        user.setEmail(EMAIL);
        Assertions.assertEquals(EMAIL, user.getEmail());
    }

    @Test
    public void getBannerIdTest() {
        User user = new User();
        final String BANNER_ID = "BannerId";

        Assertions.assertNull(user.getBannerId());

        user.setBannerId(BANNER_ID);
        Assertions.assertEquals(BANNER_ID, user.getBannerId());
    }

    @Test
    public void setBannerIdTest() {
        User user = new User();
        final String BANNER_ID = "BannerId";

        user.setBannerId(BANNER_ID);
        Assertions.assertEquals(BANNER_ID, user.getBannerId());
    }

    @Test
    public void getPasswordTest() {
        User user = new User();
        final String PASSWORD = "topsecret";

        Assertions.assertNull(user.getPassword());

        user.setPassword(PASSWORD);
        Assertions.assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void setPasswordTest() {
        User user = new User();
        final String PASSWORD = "topsecret";

        user.setPassword(PASSWORD);
        Assertions.assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void getRoleTest() {
        User user = new User();
        final String ROLE = UserConstants.USER_ROLE_ADMIN;

        Assertions.assertNull(user.getRole());

        user.setRole(ROLE);
        Assertions.assertEquals(ROLE, user.getRole());
    }

    @Test
    public void setRoleTest() {
        User user = new User();
        final String ROLE = UserConstants.USER_ROLE_MANAGER;

        user.setRole(ROLE);
        Assertions.assertEquals(ROLE, user.getRole());
    }

    @Test
    public void getVerifiedTest() {
        User user = new User();
        final Boolean VERIFIED = false;

        Assertions.assertNull(user.getVerified());

        user.setVerified(VERIFIED);
        Assertions.assertEquals(VERIFIED, user.getVerified());
    }

    @Test
    public void setVerifiedTest() {
        User user = new User();
        final Boolean VERIFIED = false;

        user.setVerified(VERIFIED);
        Assertions.assertEquals(VERIFIED, user.getVerified());
    }
}
