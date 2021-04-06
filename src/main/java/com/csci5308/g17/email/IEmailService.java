package com.csci5308.g17.email;

import javax.mail.MessagingException;

public interface IEmailService {
    public void sendEmail(String email,String emailContent) throws MessagingException;
}