package com.csci5308.g17.user;

import javax.mail.MessagingException;
import java.util.List;

public interface IVarifyPasswordEmailService {
    public void sendVarificationEmail(List<String> emailList, String token, String formLink) throws MessagingException;
}
