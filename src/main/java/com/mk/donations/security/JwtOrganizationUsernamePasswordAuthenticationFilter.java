package com.mk.donations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.donations.model.Admin;
import com.mk.donations.model.Organization;
import com.mk.donations.model.UserCredentials;
import com.mk.donations.repository.AdminRepository;
import com.mk.donations.repository.OrganizationRepository;
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

public class JwtOrganizationUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    static final Logger LOGGER = LoggerFactory.getLogger(JwtOrganizationUsernamePasswordAuthenticationFilter.class);

    private final JwtTokenProvider jwtUtil;
    private final OrganizationRepository organizationRepository;
    private final AuthenticationManager authenticationManager;

    public JwtOrganizationUsernamePasswordAuthenticationFilter(String filterProcessingUrl,
                                                        JwtTokenProvider jwtUtil,
                                                        OrganizationRepository organizationRepository,
                                                        AuthenticationManager authenticationManager) {
        super.setFilterProcessesUrl(filterProcessingUrl);
        this.jwtUtil = jwtUtil;
        this.organizationRepository = organizationRepository;
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
        Organization organization = organizationRepository.findByEmail(coreUser.getUsername()).get();
        Map<String, Object> tokenClaims = new HashMap<>();
        tokenClaims.put("sub", organization.getUsername()); // sets token's subject
        tokenClaims.put("userId", organization.getId());
        String authority = organization.getAuthorities().iterator().next().getAuthority();
        tokenClaims.put("userAuthorities", authority);
        String token = jwtUtil.generateToken(tokenClaims);
        res.addHeader(jwtUtil.getHeaderString(), jwtUtil.getTokenPrefix() + token);
        res.setHeader("Access-Control-Expose-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        ObjectMapper mapper = new ObjectMapper();
        res.getWriter().write(mapper.writeValueAsString(organization));
        LOGGER.info("Authentication successful for user {}", organization.getUsername());
    }
}
