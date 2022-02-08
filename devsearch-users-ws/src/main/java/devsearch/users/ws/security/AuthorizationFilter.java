package devsearch.users.ws.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.UserRepository;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository) {
	super(authManager);
	this.userRepository = userRepository;
    }

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
	    throws IOException, ServletException {

	String header = req.getHeader(SecurityConstants.HEADER_STRING);

	if (header == null || !header.startsWith(SecurityConstants.TOKER_PREFIX)) {
	    chain.doFilter(req, res);
	    return;
	}

	UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	String token = request.getHeader(SecurityConstants.HEADER_STRING);
	if (token != null) {
	    token = token.replace(SecurityConstants.TOKER_PREFIX, "");

	    String username = Jwts.parser()
		    .setSigningKey(SecurityConstants.getTokenSecret())
		    .parseClaimsJws(token)
		    .getBody()
		    .getSubject();

	    if (username != null) {
		UserEntity userEntity = userRepository.findByUsername(username);
		UserPrincipal userPrincipal = new UserPrincipal(userEntity);
		return new UsernamePasswordAuthenticationToken(username, null, userPrincipal.getAuthorities());
	    }

	    return null;
	}

	return null;
    }
}
