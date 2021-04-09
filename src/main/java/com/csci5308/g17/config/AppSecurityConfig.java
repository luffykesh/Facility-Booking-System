package com.csci5308.g17.config;

import com.csci5308.g17.auth.AuthSuccessHandler;
import com.csci5308.g17.user.UserConstants;
import com.csci5308.g17.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    //https://docs.spring.io/spring-security/site/docs/4.2.20.RELEASE/guides/html5/form-javaconfig.html
    // https://spring.io/guides/gs/securing-web/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(
                    "/css/**",
                    "/js/**",
                    "/forgot_password",
                    "/reset_password/**",
                    "/user_registration_form"
                ).permitAll()
                .antMatchers( "/admin_dashboard").hasAuthority(UserConstants.USER_ROLE_ADMIN)
                .antMatchers( "/manager_dashboard").hasAuthority(UserConstants.USER_ROLE_MANAGER)
                .antMatchers( "/user_dashboard").hasAuthority(UserConstants.USER_ROLE_USER)
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(authHandler())
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(
            ApplicationContextProvider.getContext().getBean(PasswordEncoder.class));
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler authHandler(){
        return new AuthSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
