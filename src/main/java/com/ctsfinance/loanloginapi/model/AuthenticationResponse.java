package com.ctsfinance.loanloginapi.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private final String jwt;
    private final String role;

    public AuthenticationResponse(String jwt, String role) {
        this.jwt = jwt;
        this.role = role;
    }

    public String getJwt(){
        return jwt;
    }

    public String getRole(){
        return role;
    }
}
