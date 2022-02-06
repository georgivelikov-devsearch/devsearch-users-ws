package devsearch.users.ws.exception;

public enum ExceptionMessages {

    INTERNAL_SERVER_ERROR("Internal Server Error", "exc_000"),
    RECORD_ALREADY_EXISTS_WITH_THIS_ID("Record with this id already exists", "exc_001"),
    RECORD_ALREADY_EXISTS_WITH_THIS_USERNAME("Record with this username already exists", "exc_002"),
    RECORD_ALREADY_EXISTS_WITH_THIS_EMAIL("Record with this email already exists", "exc_003"),
    NO_RECORD_FOUND_WITH_THIS_ID("Record with provided id is not found", "exc_004"),
    NO_RECORD_FOUND_WITH_THIS_USERNAME("Record with provided username is not found", "exc_005"),
    NO_RECORD_FOUND_WITH_THIS_EMAIL("Record with provided email is not found", "exc_006"),
    INVALID_FIELD("Field is missing or not valid. Please check documentation for required fields.", "exc_007"),
    AUTHENTICATION_FAILED("Authentication failed", "exc_008"),
    COULD_NOT_CREATE_RECORD("Could not create record", "exc_009"),
    COULD_NOT_UPDATE_RECORD("Could not update record", "exc_010"),
    COULD_NOT_DELETE_RECORD("Could not delete record", "exc_011"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified", "exc_012");

    private String exceptionMessage;
    private String exceptionCode;

    ExceptionMessages(String errorMessage, String exceptionCode) {
	this.exceptionMessage = errorMessage;
	this.setExceptionCode(exceptionCode);
    }

    public String getExceptionMessage() {
	return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
	this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionCode() {
	return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
	this.exceptionCode = exceptionCode;
    }
}
