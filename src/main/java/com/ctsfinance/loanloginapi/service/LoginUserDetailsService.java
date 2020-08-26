package com.ctsfinance.loanloginapi.service;

import com.ctsfinance.loanloginapi.model.Login;
import com.ctsfinance.loanloginapi.repository.LoginRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetailsService.class);

    @Autowired
    private LoginRepo loginRepo;

    public Login loadUserByUsername(String username) throws Exception {

        LOGGER.info("Start loadUserByUsername:: LoginUserDetailsService");

        Login dbDetails = loginRepo.findByUsername(username);

        if(dbDetails == null)
            throw new RuntimeException("");//UsernameNotFoundException("No record found");

        return dbDetails;
    }
}
