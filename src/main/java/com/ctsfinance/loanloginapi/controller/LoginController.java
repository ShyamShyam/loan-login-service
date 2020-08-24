package com.ctsfinance.loanloginapi.controller;


import com.ctsfinance.loanloginapi.model.Login;
import com.ctsfinance.loanloginapi.model.LoginMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-authorization")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @PostMapping
    //@PreAuthorize("hasAuthority('user:read', 'user:write')")
    @HystrixCommand(fallbackMethod = "getFallbackUserLogin")
    public ResponseEntity<Login> userLogin(@RequestBody LoginMapper loginMapper){

        LOGGER.info("Start userLogin::LoginController");

        Login login = new Login();

        login.setUserName(loginMapper.getUserName());
        login.setPassword(loginMapper.getPassword());

        return ResponseEntity.ok(login);
    }

    public ResponseEntity<String> getFallbackUserLogin(@RequestBody Login login){
        return ResponseEntity.ok("No details found: " + login);
    }
}
