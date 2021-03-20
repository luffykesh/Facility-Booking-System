package com.csci5308.g17.auth;

import java.io.IOException;
import java.util.Set;
import com.csci5308.g17.user.UserConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException{
        Set<String> userRoles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if(userRoles.contains(UserConstants.USER_ROLE_ADMIN)) {
            response.sendRedirect("/admin/home");
        }
        else if(userRoles.contains(UserConstants.USER_ROLE_MANAGER)) {
            response.sendRedirect("/manager/home");
        }
        else {
            response.sendRedirect("/user/home");
        }
    }
}
