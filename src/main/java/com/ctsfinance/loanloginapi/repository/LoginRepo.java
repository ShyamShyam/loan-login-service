package com.ctsfinance.loanloginapi.repository;

import com.ctsfinance.loanloginapi.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LoginRepo extends JpaRepository<Login, Long> {

    Login findByUserName(String email);
}
