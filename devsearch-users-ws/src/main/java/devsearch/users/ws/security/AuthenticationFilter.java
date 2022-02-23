package devsearch.users.ws.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import devsearch.users.ws.SpringApplicationContext;
import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.ui.model.request.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
	this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
	    throws AuthenticationException {

	try {
	    LoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
	    return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
		    creds.getPassword(), new ArrayList<>()));
	} catch (IOException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
	    Authentication auth) throws IOException, ServletException {

	String username = ((UserPrincipal) auth.getPrincipal()).getUsername();
	Date expirationDate = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
	String token = Jwts.builder()
		.setSubject(username)
		.setExpiration(expirationDate)
		.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
		.compact();

	// User service can't be injected here because AuthenticationFilter is not a
	// bean
	UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
	UserDto userDto;
	try {
	    userDto = userService.getUserForLogin(username);
	} catch (RestApiUsersException ex) {
	    throw new ServletException(ex);
	}

	res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKER_PREFIX + token);
	res.addHeader(SecurityConstants.USER_ID, userDto.getUserId());
    }
}
