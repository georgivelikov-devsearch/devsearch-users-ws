package devsearch.users.ws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import devsearch.users.ws.io.entity.AuthorityEntity;
import devsearch.users.ws.io.entity.ConfigEntity;
import devsearch.users.ws.io.entity.RoleEntity;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.AuthorityRepository;
import devsearch.users.ws.io.repository.ConfigRepository;
import devsearch.users.ws.io.repository.RoleRepository;
import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.shared.utils.AppConstants;
import devsearch.users.ws.shared.utils.Utils;

@Component
public class InitialUserSetup {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Utils utils;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent ev) {
	ConfigEntity initialSetupConfigured = configRepository.findByName(InitialConstants.INITIAL_SETUP_CONFIG);
	if (initialSetupConfigured != null && Boolean.valueOf(initialSetupConfigured.getValue())) {
	    return;
	}

	initialSetupConfigured = new ConfigEntity();
	initialSetupConfigured.setName(InitialConstants.INITIAL_SETUP_CONFIG);
	initialSetupConfigured.setValue("true");
	configRepository.save(initialSetupConfigured);

	createAuthorities();
	createRoles();
	createAdmin();
    }

    @Transactional
    private void createAuthorities() {
	for (String authorityName : InitialConstants.AUTHORITIES) {
	    AuthorityEntity authority = authorityRepository.findByName(authorityName);
	    if (authority == null) {
		authority = new AuthorityEntity(authorityName);
		authorityRepository.save(authority);
	    }
	}
    }

    @Transactional
    private void createRoles() {
	for (String roleName : InitialConstants.ROLES) {
	    RoleEntity role = roleRepository.findByName(roleName);
	    if (role == null) {
		role = new RoleEntity(roleName);
		List<String> authorityNames = InitialConstants.ROLE_MAP.get(roleName);
		List<AuthorityEntity> authorities = new ArrayList<>();
		for (String authorityName : authorityNames) {
		    AuthorityEntity authority = authorityRepository.findByName(authorityName);
		    if (authority != null) {
			authorities.add(authority);
		    }
		}

		role.setAuthorities(authorities);
		roleRepository.save(role);
	    }
	}
    }

    @Transactional
    private UserEntity createAdmin() {
	UserEntity admin = new UserEntity();
	admin.setUsername(InitialConstants.USERNAME);
	admin.setFirstName(InitialConstants.FIRST_NAME);
	admin.setLastName(InitialConstants.LAST_NAME);
	admin.setEmail("admin@test.com");
	admin.setUserId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	admin.setEncryptedPassword(bCryptPasswordEncoder.encode(InitialConstants.INITIAL_PASSWORD));

	List<RoleEntity> roles = new ArrayList<>();
	for (String roleName : InitialConstants.ADMINISTRATOR_USER_ROLES) {
	    RoleEntity role = roleRepository.findByName(roleName);
	    if (role != null) {
		roles.add(role);
	    }
	}

	admin.setRoles(roles);

	return userRepository.save(admin);
    }
}
