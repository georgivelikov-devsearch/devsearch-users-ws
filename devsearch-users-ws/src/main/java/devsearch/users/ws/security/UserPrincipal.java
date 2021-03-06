package devsearch.users.ws.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import devsearch.users.ws.io.entity.AuthorityEntity;
import devsearch.users.ws.io.entity.RoleEntity;
import devsearch.users.ws.io.entity.UserEntity;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 8428393208131543397L;

    private UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
	this.userEntity = userEntity;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	List<GrantedAuthority> authorities = new ArrayList<>();
	List<AuthorityEntity> authorityEntities = new ArrayList<>();

	Collection<RoleEntity> roles = userEntity.getRoles();
	if (roles == null) {
	    return authorities;
	}

	roles.forEach((role) -> {
	    authorities.add(new SimpleGrantedAuthority(role.getName()));
	    authorityEntities.addAll(role.getAuthorities());
	});

	authorityEntities.forEach((authorityEntity) -> {
	    authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
	});

	return authorities;
    }
}
