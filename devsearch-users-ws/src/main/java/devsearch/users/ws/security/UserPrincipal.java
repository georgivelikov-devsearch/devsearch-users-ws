package devsearch.users.ws.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import devsearch.users.ws.io.entity.UserEntity;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 8428393208131543397L;

    private UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
	this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	return new ArrayList<>();
    }

    @Override
    public String getPassword() {
	return userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
	return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }

}
