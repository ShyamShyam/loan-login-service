package com.ctsfinance.loanloginapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String userName;
    private String password;
}
