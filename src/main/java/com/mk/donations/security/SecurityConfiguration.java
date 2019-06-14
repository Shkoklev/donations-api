package com.mk.donations.security;

import com.mk.donations.service.impl.AdminServiceImpl;
import com.mk.donations.service.impl.DonorServiceImpl;
import com.mk.donations.service.impl.OrganizationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(1)
    public static class AdminSecurity extends WebSecurityConfigurerAdapter {

        private final AuthenticationSuccessHandler successHandler;
        private final AuthenticationFailureHandler failureHandler;
        private final LogoutSuccessHandler logoutSuccessHandler;
        private final AuthenticationEntryPoint authenticationEntryPoint;
        private final AdminServiceImpl adminService;
        private final PasswordEncoder passwordEncoder;

        public AdminSecurity(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, LogoutSuccessHandler logoutSuccessHandler, AuthenticationEntryPoint authenticationEntryPoint, AdminServiceImpl adminService, PasswordEncoder passwordEncoder) {
            this.successHandler = successHandler;
            this.failureHandler = failureHandler;
            this.logoutSuccessHandler = logoutSuccessHandler;
            this.authenticationEntryPoint = authenticationEntryPoint;
            this.adminService = adminService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(adminService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors();
            http.csrf().disable();
            http.httpBasic().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint);

            http
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/admin/register").permitAll()
                    .antMatchers("/admin/login").permitAll()
                    .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/admin/login")
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                    .headers()
                    .frameOptions()
                    .disable();
        }
    }

    @Configuration
    @Order(2)
    public static class DonorSecurity extends WebSecurityConfigurerAdapter {

        private final AuthenticationSuccessHandler successHandler;
        private final AuthenticationFailureHandler failureHandler;
        private final LogoutSuccessHandler logoutSuccessHandler;
        private final AuthenticationEntryPoint authenticationEntryPoint;
        private final DonorServiceImpl donorService;
        private final PasswordEncoder passwordEncoder;

        public DonorSecurity(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, LogoutSuccessHandler logoutSuccessHandler, AuthenticationEntryPoint authenticationEntryPoint, DonorServiceImpl donorService, PasswordEncoder passwordEncoder) {
            this.successHandler = successHandler;
            this.failureHandler = failureHandler;
            this.logoutSuccessHandler = logoutSuccessHandler;
            this.authenticationEntryPoint = authenticationEntryPoint;
            this.donorService = donorService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(donorService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors();
            http.csrf().disable();
            http.httpBasic().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint);

            http
                    .antMatcher("/donors/**")
                    .authorizeRequests()
                    .antMatchers("/donors/register").permitAll()
                    .antMatchers("/donors/login").permitAll()
                    .antMatchers("/donors/loggedDonor")
                    .hasAuthority("DONOR")
                    .antMatchers("/donors/donate_to_organization/**")
                    .hasAuthority("DONOR")
                    .antMatchers("/donors")
                    .authenticated()
                    .antMatchers("/donors/{\\d+}")
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/donors/login")
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/donors/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                    .headers()
                    .frameOptions()
                    .disable();
        }
    }

    @Configuration
    @Order(3)
    public static class OrganizationSecurity extends WebSecurityConfigurerAdapter {

        private final AuthenticationSuccessHandler successHandler;
        private final AuthenticationFailureHandler failureHandler;
        private final LogoutSuccessHandler logoutSuccessHandler;
        private final AuthenticationEntryPoint authenticationEntryPoint;
        private final OrganizationServiceImpl organizationService;
        private final PasswordEncoder passwordEncoder;

        public OrganizationSecurity(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, LogoutSuccessHandler logoutSuccessHandler, AuthenticationEntryPoint authenticationEntryPoint, OrganizationServiceImpl organizationService, PasswordEncoder passwordEncoder) {
            this.successHandler = successHandler;
            this.failureHandler = failureHandler;
            this.logoutSuccessHandler = logoutSuccessHandler;
            this.authenticationEntryPoint = authenticationEntryPoint;
            this.organizationService = organizationService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(organizationService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors();
            http.csrf().disable();
            http.httpBasic().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint);

            http
                    .authorizeRequests()
                    .antMatchers("/organizations/register").permitAll()
                    .antMatchers("/organizations/login").permitAll()
                    .antMatchers("/organizations/loggedOrganization")
                    .hasAuthority("ORGANIZATION")
                    .antMatchers("/organizations/{\\d+}/add_demand")
                    .hasAnyAuthority("ORGANIZATION", "ADMIN")
                    .antMatchers("/organizations/{\\d+}/change_demand_quantity")
                    .hasAnyAuthority("ORGANIZATION", "ADMIN")
                    .antMatchers("/organizations/{\\d+}/delete_demand")
                    .hasAnyAuthority("ORGANIZATION", "ADMIN")
                    .antMatchers("/organizations/{\\d+}/accept_donation/**")
                    .hasAnyAuthority("ORGANIZATION")
                    .antMatchers("/organizations/{\\d+}/decline_donation/**")
                    .hasAnyAuthority("ORGANIZATION")
                    .antMatchers("/organizations/{\\d+}/successful_donations")
                    .hasAnyAuthority("ORGANIZATION")
                    .antMatchers("/organizations/{\\d+}/pending_donations")
                    .hasAnyAuthority("ORGANIZATION")
                    .antMatchers("/organizations/{\\d+}/declined_donations")
                    .hasAnyAuthority("ORGANIZATION")
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/organizations/login")
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/organizations/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                    .headers()
                    .frameOptions()
                    .disable();
        }
    }

    @Bean
    public AuthenticationSuccessHandler getSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());
        };
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) ->
                response.setStatus(HttpStatus.OK.value());
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            /**
             * Always returns a 401 error code to the client.
             */
            @Override
            public void commence(HttpServletRequest request,
                                 HttpServletResponse response,
                                 AuthenticationException authenticationException) throws IOException,
                    ServletException {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
