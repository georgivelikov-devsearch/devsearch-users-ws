package devsearch.users.ws.ui.model.response;

public class UserResponse {

    private String publicId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public String getUserId() {
	return publicId;
    }

    public void setUserId(String publicId) {
	this.publicId = publicId;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }
}
