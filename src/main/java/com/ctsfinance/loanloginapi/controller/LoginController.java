package com.ctsfinance.loanloginapi.controller;


import com.ctsfinance.loanloginapi.model.*;
import com.ctsfinance.loanloginapi.service.LoginUserDetailsService;
import com.ctsfinance.loanloginapi.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @PostMapping("/v1/user-login")
    //@PreAuthorize("hasAuthority('user:read', 'user:write')")
    //@HystrixCommand(fallbackMethod = "getFallbackUserLogin")
    public ResponseEntity<AuthenticationResponse> userLogin(@RequestBody LoginMapper loginMapper) throws Exception{

        LOGGER.info("Start userLogin::LoginController");

        //authenticate(loginMapper.getUsername(), loginMapper.getPassword());

        final Login userDetails = loginUserDetailsService.loadUserByUsername(loginMapper.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(token, userDetails.getUserType()));
    }

    @GetMapping("/getUserDetails")
    //@PreAuthorize("hasAuthority('user:read', 'user:write')")
    //@HystrixCommand(fallbackMethod = "getFallbackUserLogin")
    public ResponseEntity<CustomeUser> getUSerDetails(@Param("username") String username) throws Exception{

        LOGGER.info("Start getUSerDetails::LoginController");

        final Login userDetails = loginUserDetailsService.loadUserByUsername(username);

        return ResponseEntity.ok(new CustomeUser(userDetails.getUsername(), userDetails.getPassword(), new ArrayList<>()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS" + e + " user: " + username + " pwd: " + password);
        }
    }

//    public ResponseEntity<String> getFallbackUserLogin(@RequestBody Login login){
//        return ResponseEntity.ok("No details found: " + login);
//    }
}
