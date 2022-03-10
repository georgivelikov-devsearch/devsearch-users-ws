package devsearch.users.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.service.UserService;

@EnableGlobalMethodSecurity(securedEnabled = true)
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
		.cors()
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
		.permitAll()
		.antMatchers(HttpMethod.GET, SecurityConstants.STATUS_URL)
		.permitAll()
		.antMatchers(HttpMethod.GET, "/profiles/public")
		.permitAll()
		.antMatchers(HttpMethod.GET, "/profiles/public/*")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.addFilter(getAuthenticationFilter(SecurityConstants.LOGIN_URL))
		.addFilter(getAuthorizationFilter())
		.exceptionHandling()
		.accessDeniedHandler(new AccessDeniedHandlerImpl())
		.authenticationEntryPoint(new AuthenticationEntryPointImpl())
		.and()
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

    /*
     * Needed when browser is calling directly the micro service. When Gateway
     * service is calling the service, it is not needed
     */
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//	final CorsConfiguration configuration = new CorsConfiguration();
//
//	configuration.setAllowCredentials(true);
//	configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//	configuration.setAllowedMethods(Arrays.asList("*"));
//	configuration.setAllowedHeaders(Arrays.asList("*"));
//	configuration.addExposedHeader(SecurityConstants.HEADER_STRING);
//	configuration.addExposedHeader(SecurityConstants.USER_ID);
//	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	source.registerCorsConfiguration("/**", configuration);
//
//	return source;
//    }
}
