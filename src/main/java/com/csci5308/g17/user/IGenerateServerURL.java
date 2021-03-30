package com.csci5308.g17.user;

import javax.servlet.http.HttpServletRequest;

public interface IGenerateServerURL {
    String getURL(HttpServletRequest request);
}
