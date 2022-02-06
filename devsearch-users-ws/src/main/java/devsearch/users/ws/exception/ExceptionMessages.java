package devsearch.users.ws.exception;

public enum ExceptionMessages {
    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS_WITH_THIS_ID("Record with this id already exists"),
    RECORD_ALREADY_EXISTS_WITH_THIS_USERNAME("Record with this username already exists"),
    RECORD_ALREADY_EXISTS_WITH_THIS_EMAIL("Record with this email already exists"),
    NO_RECORD_FOUND_WITH_THIS_ID("Record with provided id is not found"),
    NO_RECORD_FOUND_WITH_THIS_USERNAME("Record with provided username is not found"),
    NO_RECORD_FOUND_WITH_THIS_EMAIL("Record with provided email is not found"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_CREATE_RECORD("Could not create record"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

    private String exceptionMessage;

    ExceptionMessages(String errorMessage) {
	this.exceptionMessage = errorMessage;
    }

    public String getExceptionMessage() {
	return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
	this.exceptionMessage = exceptionMessage;
    }
}
