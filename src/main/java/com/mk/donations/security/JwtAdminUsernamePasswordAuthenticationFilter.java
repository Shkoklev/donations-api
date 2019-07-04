package com.mk.donations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.donations.model.Admin;
import com.mk.donations.model.UserCredentials;
import com.mk.donations.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JwtAdminUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    static final Logger LOGGER = LoggerFactory.getLogger(JwtAdminUsernamePasswordAuthenticationFilter.class);

    private final JwtTokenProvider jwtUtil;
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;

    public JwtAdminUsernamePasswordAuthenticationFilter(String filterProcessingUrl,
                                                        JwtTokenProvider jwtUtil,
                                                        AdminRepository adminRepository,
                                                        AuthenticationManager authenticationManager) {
        super.setFilterProcessesUrl(filterProcessingUrl);
        this.jwtUtil = jwtUtil;
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    credentials.email,
                    credentials.password,
                    new ArrayList<>());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        UserDetails coreUser
                = (UserDetails) auth.getPrincipal();
        Admin admin = adminRepository.findByEmail(coreUser.getUsername()).get();
        Map<String, Object> tokenClaims = new HashMap<>();
        tokenClaims.put("sub", admin.getUsername()); // sets token's subject
        tokenClaims.put("userId", admin.getId());
        String authority = admin.getAuthorities().iterator().next().getAuthority();
        tokenClaims.put("userAuthorities", authority);
        String token = jwtUtil.generateToken(tokenClaims);
        res.addHeader(jwtUtil.getHeaderString(), jwtUtil.getTokenPrefix() + token);
        res.setHeader("Access-Control-Expose-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        ObjectMapper mapper = new ObjectMapper();
        res.getWriter().write(mapper.writeValueAsString(admin));
        LOGGER.info("Authentication successful for user {}", admin.getUsername());
    }
}
