package devsearch.users.ws.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.google.common.net.HttpHeaders;

import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.UserRepository;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final String tokenSecret;
    private final String tokenPrefix;
    private final UserRepository userRepository;

    public AuthorizationFilter(Environment env, AuthenticationManager authManager, UserRepository userRepository) {
	super(authManager);
	this.userRepository = userRepository;
	this.tokenSecret = env.getProperty(SecurityConstants.TOKEN_SECRET_KEY);
	this.tokenPrefix = env.getProperty(SecurityConstants.TOKEN_PREFIX_KEY);
    }

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
	    throws IOException, ServletException {

	String header = req.getHeader(HttpHeaders.AUTHORIZATION);

	if (header == null || !header.startsWith(tokenPrefix)) {
	    chain.doFilter(req, res);
	    return;
	}

	UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	String token = request.getHeader(HttpHeaders.AUTHORIZATION);
	if (token != null) {
	    token = token.replace(tokenPrefix, "");

	    String username = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject();

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
