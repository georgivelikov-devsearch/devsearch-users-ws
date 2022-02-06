package devsearch.users.ws.exception;

public class UsersRestApiException extends Exception {

    private static final long serialVersionUID = -2200242070640018947L;

    private String sourceExceptionMessage;

    public UsersRestApiException(String message) {
	super(message);
    }

    public UsersRestApiException(ExceptionMessages message) {
	super(message.getExceptionMessage());
    }

    public UsersRestApiException(String message, String sourceExceptionMessage) {
	super(message);
	this.sourceExceptionMessage = sourceExceptionMessage;
    }

    public String getSourceExceptionMessage() {
	return this.sourceExceptionMessage;
    }

}
