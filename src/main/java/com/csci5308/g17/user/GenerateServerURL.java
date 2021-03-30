package com.csci5308.g17.user;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
public class GenerateServerURL implements IGenerateServerURL {
    @Override
    public String getURL(HttpServletRequest request){
        String serverUrl = String.format(
                "%s://%s:%d",
                request.getScheme(), request.getServerName(), request.getServerPort());
        return serverUrl;

    }
}
