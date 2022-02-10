package devsearch.users.ws.io.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 6023605726818532743L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String publicId;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 120)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @ManyToMany(mappedBy = "users")
    private Collection<RoleEntity> roles;

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

    public String getEncryptedPassword() {
	return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
	this.encryptedPassword = encryptedPassword;
    }

    public Collection<RoleEntity> getRoles() {
	return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
	this.roles = roles;
    }
}
