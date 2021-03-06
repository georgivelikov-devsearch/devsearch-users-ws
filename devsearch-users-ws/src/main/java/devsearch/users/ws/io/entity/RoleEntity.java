package devsearch.users.ws.io.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -7921312652602859148L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = { CascadeType.PERSIST
    })
    @JoinTable(name = "roles_authorities", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
	    inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;

    public RoleEntity() {
    }

    public RoleEntity(String name) {
	this.name = name;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Collection<UserEntity> getUsers() {
	return users;
    }

    public void setUsers(Collection<UserEntity> users) {
	this.users = users;
    }

    public Collection<AuthorityEntity> getAuthorities() {
	return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
	this.authorities = authorities;
    }
}
