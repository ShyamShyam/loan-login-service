package com.ctsfinance.loanloginapi.repository;

import com.ctsfinance.loanloginapi.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginRepo extends JpaRepository<Login, Long> {

    @Query(value = "SELECT * FROM user_login u WHERE u.username = :username", nativeQuery = true)
    Login findByUsername(@Param("username") String username);
}
