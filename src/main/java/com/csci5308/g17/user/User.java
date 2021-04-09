package com.csci5308.g17.user;

public class User {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String bannerId;
    private Boolean isVerified;
    private String token;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        this.isVerified = verified;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object other) {

        if(this == other) {
            return true;
        }

        if(other == null || getClass() != other.getClass()) {
            return false;
        }

        User u2 = (User)other;

        boolean isPasswordEqual = (
            (this.password == u2.password) || (this.password.equals(u2.password)) );

        return (
            this.email.equals(u2.email)
            && this.name.equals(u2.name)
            && isPasswordEqual
            && this.role.equals(u2.role)
            && this.bannerId.equals(u2.bannerId)
            && this.isVerified.equals(u2.isVerified)
        );
    }

    @Override
    public String toString() {
        String userStr = String.format(
            "User(id=%d, email=%s, name=%s, role=%s, bannerId=%s, verified=%s)",
            id,
            email,
            name,
            role,
            bannerId,
            isVerified
        );
        return userStr;
    }
}
