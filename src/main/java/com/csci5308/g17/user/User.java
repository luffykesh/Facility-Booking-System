package com.csci5308.g17.user;

public class User {

    Integer id;
    String name;
    String email;
    String password;
    String role;
    String bannerId;
    Boolean isVerified;

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

    @Override
    public boolean equals(Object other) {

        if(this == other) {
            return true;
        }

        if(other == null || getClass() != other.getClass()) {
            return false;
        }

        User u2 = (User)other;

        return (
            this.email.equals(u2.email)
            && this.name.equals(u2.name)
            && this.password.equals(u2.password)
            && this.role.equals(u2.role)
            && this.bannerId.equals(u2.bannerId)
            && this.isVerified.equals(u2.isVerified)
        );
    }

}
