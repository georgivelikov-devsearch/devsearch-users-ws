package devsearch.users.ws.exception;

public class UsersRestApiException extends Exception {

    private static final long serialVersionUID = -2200242070640018947L;

    private String sourceExceptionMessage;
    private String exceptionCode;

    public UsersRestApiException(String message) {
	super(message);
    }

    public UsersRestApiException(ExceptionMessages message) {
	super(message.getExceptionMessage());
	this.exceptionCode = message.getExceptionCode();
    }

    public UsersRestApiException(ExceptionMessages message, String sourceExceptionMessage) {
	super(message.getExceptionMessage());
	this.exceptionCode = message.getExceptionCode();
	this.sourceExceptionMessage = sourceExceptionMessage;
    }

    public UsersRestApiException(String message, String exceptionCode, String sourceExceptionMessage) {
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
