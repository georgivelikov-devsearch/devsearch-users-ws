package devsearch.users.ws.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import devsearch.users.ws.exception.ExceptionMessageRest;
import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.shared.utils.Constants;
import devsearch.users.ws.shared.utils.Utils;

@ControllerAdvice
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Autowired
    private Utils utils;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
	    AccessDeniedException accessDeniedException) throws IOException, ServletException {
	String dateStr = utils.getDateString(new Date());
	String path = request.getRequestURI().toString();
	String method = request.getMethod().toString();
	ExceptionMessageRest exception = new ExceptionMessageRest(dateStr, path, method,
		accessDeniedException.getMessage(), ExceptionMessages.ACCESS_FORBIDDEN.getExceptionCode(),
		ExceptionMessages.ACCESS_FORBIDDEN.getExceptionMessage());

	ObjectMapper mapper = new ObjectMapper();

	response.setStatus(HttpStatus.FORBIDDEN.value());
	response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	response.getWriter().write(mapper.writeValueAsString(exception));
    }

}
