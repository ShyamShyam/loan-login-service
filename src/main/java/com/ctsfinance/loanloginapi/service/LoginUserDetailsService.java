package com.ctsfinance.loanloginapi.service;

import com.ctsfinance.loanloginapi.model.Login;
import com.ctsfinance.loanloginapi.repository.LoginRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetailsService.class);

    @Autowired
    private LoginRepo loginRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        LOGGER.info("Start loadUserByUsername:: LoginUserDetailsService");

        Login dbDetails = loginRepo.findByUserName(userName);

        if(dbDetails == null)
            throw new UsernameNotFoundException("No record found");

        return new User(dbDetails.getUserName(), dbDetails.getPassword(), new ArrayList<>());
    }
}
