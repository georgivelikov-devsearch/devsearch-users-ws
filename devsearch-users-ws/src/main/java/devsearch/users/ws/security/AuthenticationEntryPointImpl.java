package devsearch.users.ws.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import devsearch.users.ws.exception.ExceptionMessageRest;
import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.shared.utils.AppConstants;
import devsearch.users.ws.shared.utils.Utils;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private Utils utils = new Utils();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException authException) throws IOException, ServletException {
	String dateStr = utils.getDateString(new Date());
	String path = request.getRequestURI().toString();
	String method = request.getMethod().toString();
	ExceptionMessageRest exception = new ExceptionMessageRest(dateStr, path, method, authException.getMessage(),
		ExceptionMessages.AUTHENTICATION_FAILED.getExceptionCode(),
		ExceptionMessages.AUTHENTICATION_FAILED.getExceptionMessage());

	ObjectMapper mapper = new ObjectMapper();

	response.setStatus(HttpStatus.UNAUTHORIZED.value());
	response.setCharacterEncoding(AppConstants.DEFAULT_ENCODING);
	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	response.getWriter().write(mapper.writeValueAsString(exception));
    }
}
