package com.csci5308.g17.user;

import javax.mail.MessagingException;

public interface IEmailService {
    public void sendEmail(String emailId, String token, String formLink,String emailContent) throws MessagingException;
}