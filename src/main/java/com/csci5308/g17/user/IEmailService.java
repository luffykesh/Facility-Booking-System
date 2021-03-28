package com.csci5308.g17.user;

import javax.mail.MessagingException;

public interface IEmailService {
    public void sendResetPasswordEmail(String emailId, String token, String formLink) throws MessagingException;
}