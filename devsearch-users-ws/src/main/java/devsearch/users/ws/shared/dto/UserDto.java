package devsearch.users.ws.shared.dto;

public class UserDto {

    private long id;
    private String publicId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getPublicId() {
	return publicId;
    }

    public void setPublicId(String publicId) {
	this.publicId = publicId;
    }

    public String getUserame() {
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

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEncryptedPassword() {
	return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
	this.encryptedPassword = encryptedPassword;
    }

}
