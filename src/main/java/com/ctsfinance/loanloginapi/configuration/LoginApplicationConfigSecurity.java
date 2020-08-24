package com.ctsfinance.loanloginapi.configuration;

import com.ctsfinance.loanloginapi.controller.JwtAuthenticationController;
import com.ctsfinance.loanloginapi.filter.JwtRequestFilter;
import com.ctsfinance.loanloginapi.service.LoginUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ctsfinance.loanloginapi.authentication.UserRoles.*;

@Configuration
@EnableWebSecurity
public class LoginApplicationConfigSecurity extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginApplicationConfigSecurity.class);

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info("Start configure method::AuthenticationManagerBuilder");
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
       // Use BCryptPasswordEncoder
        auth.userDetailsService(loginUserDetailsService).passwordEncoder(passwordEncoder());

        LOGGER.info("Exit configure method::AuthenticationManagerBuilder");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        LOGGER.info("Start configure method::HttpSecurity");

        httpSecurity
                .csrf()
                .disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/", "/login/authenticate", "/index", "/css/*", "/js/*").permitAll()
                // all other requests need to be authenticated
                //.antMatchers("/login/**")
                //.hasAnyRole(USER.name())
                .anyRequest()
                .authenticated()
                .and()

                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        LOGGER.info("Exit configure method::HttpSecurity");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        LOGGER.info("authenticationManagerBean::LoginApplicationConfigSecurity");
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("passwordEncoder::LoginApplicationConfigSecurity");
        return new BCryptPasswordEncoder(10);
    }
}
