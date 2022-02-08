package devsearch.users.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
	    UserRepository userRepository) {
	this.userDetailsService = userDetailsService;
	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
		.permitAll()
		.antMatchers(HttpMethod.GET, SecurityConstants.STATUS_URL)
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.addFilter(getAuthenticationFilter(SecurityConstants.LOGIN_URL))
		.addFilter(getAuthorizationFilter())
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // No session, no cookies
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // Spring default login url is '/login'. You could change it by adding custom
    // url - '/users/login'

    private AuthenticationFilter getAuthenticationFilter(String url) throws Exception {
	final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
	if (url != null) {
	    filter.setFilterProcessesUrl(url);
	}

	return filter;
    }

    // Returns AuthorizationFilter. Both AuthenticationFilter and
    // AuthorizationFilter are needed for JWT authentication
    private AuthorizationFilter getAuthorizationFilter() throws Exception {
	return new AuthorizationFilter(authenticationManager(), userRepository);
    }
}
