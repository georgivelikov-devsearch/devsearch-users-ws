package devsearch.users.ws.exception;

public class RestApiUsersException extends Exception {

    private static final long serialVersionUID = -2200242070640018947L;

    private String sourceExceptionMessage;
    private String exceptionCode;

    public RestApiUsersException(String message) {
	super(message);
    }

    public RestApiUsersException(ExceptionMessages message) {
	super(message.getExceptionMessage());
	this.exceptionCode = message.getExceptionCode();
    }

    public RestApiUsersException(ExceptionMessages message, String sourceExceptionMessage) {
	super(message.getExceptionMessage());
	this.exceptionCode = message.getExceptionCode();
	this.sourceExceptionMessage = sourceExceptionMessage;
    }

    public RestApiUsersException(String message, String exceptionCode, String sourceExceptionMessage) {
	super(message);
	this.exceptionCode = exceptionCode;
	this.sourceExceptionMessage = sourceExceptionMessage;
    }

    public String getSourceExceptionMessage() {
	return this.sourceExceptionMessage;
    }

    public String getExceptionCode() {
	return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
	this.exceptionCode = exceptionCode;
    }

}
