package com.test.blog.security;

import com.test.blog.service.AuthService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthService authService;
    
    private static final String[] AUTH_WHITELIST = {
            "/api/users/register",
            "/api/users/login",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // disable basic authentication
                .cors()
                .and()
                .csrf().disable() // don't need csrf since won't be using sessions
                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .logout().disable() // disable default logout
                .formLogin().disable() // disable default login
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // disable sessions

        AccessFilter accessFilter = new AccessFilter(authenticationManager());
        http.addFilterBefore(accessFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
