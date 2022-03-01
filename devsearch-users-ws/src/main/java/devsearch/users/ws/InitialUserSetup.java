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
import devsearch.users.ws.io.entity.ProfileEntity;
import devsearch.users.ws.io.entity.RoleEntity;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.AuthorityRepository;
import devsearch.users.ws.io.repository.ConfigRepository;
import devsearch.users.ws.io.repository.ProfileRepository;
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
    private ProfileRepository profileRepository;

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
	createPersonalUser();
	// createUsersForTesting();
	createRandomUsers();
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
    private void createAdmin() {
	UserEntity admin = new UserEntity();
	admin.setUsername(InitialConstants.USERNAME);
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

	UserEntity newAdmin = userRepository.save(admin);

	ProfileEntity adminProfile = new ProfileEntity();
	adminProfile.setProfilePrivateId(utils.generatePublicId(AppConstants.PRIVATE_ID_LENGTH));
	adminProfile.setProfilePublicId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	adminProfile.setFirstName(InitialConstants.FIRST_NAME);
	adminProfile.setLastName(InitialConstants.LAST_NAME);
	adminProfile.setContactEmail(newAdmin.getEmail());
	adminProfile.setUserId(newAdmin.getUserId());
	adminProfile.setAdminProfile(true);
	profileRepository.save(adminProfile);
    }

    @Transactional
    private void createPersonalUser() {
	UserEntity personalUser = new UserEntity();
	personalUser.setUsername("georgivelikov");
	personalUser.setEmail("gmvelikov@gmail.com");
	personalUser.setUserId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	personalUser.setEncryptedPassword(bCryptPasswordEncoder.encode(InitialConstants.INITIAL_PASSWORD));

	List<RoleEntity> roles = new ArrayList<>();
	for (String roleName : InitialConstants.PERSONAL_USER_ROLES) {
	    RoleEntity role = roleRepository.findByName(roleName);
	    if (role != null) {
		roles.add(role);
	    }
	}

	personalUser.setRoles(roles);

	UserEntity newUser = userRepository.save(personalUser);

	ProfileEntity profile = new ProfileEntity();
	profile.setProfilePrivateId(utils.generatePublicId(AppConstants.PRIVATE_ID_LENGTH));
	profile.setProfilePublicId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	profile.setFirstName("Georgi");
	profile.setLastName("Velikov");
	profile.setContactEmail(newUser.getEmail());
	profile.setUserId(newUser.getUserId());
	profile.setShortIntro("Experienced FullStack Java and React Developer");
	profile.setAbout(
		"Praesent sit amet dolor et urna consectetur pharetra. Vestibulum vel pellentesque ipsum. Morbi iaculis sit amet lorem nec blandit. Phasellus sed augue commodo, imperdiet purus ac, feugiat neque. Sed egestas non eros mollis mollis. Suspendisse felis massa, egestas laoreet eros id, lacinia consequat nisl. Pellentesque tincidunt odio nec purus dignissim facilisis. Nulla molestie pharetra risus eget molestie. Integer facilisis sit amet nisl at aliquet. Vestibulum consectetur nunc eu lorem mattis sagittis. Sed auctor tristique mollis. Nulla blandit neque vel iaculis tristique. Nulla feugiat pulvinar nisl et eleifend.");
	profile.setLocationCity("Sofia");
	profile.setLocationCountry("Bulgaria");
	profileRepository.save(profile);
    }

    @Transactional
    private void createRandomUsers() {
	List<UserEntity> users = new ArrayList<>();
	List<ProfileEntity> profiles = new ArrayList<>();
	int counter = 0;
	while (counter < 100) {
	    UserEntity personalUser = new UserEntity();
	    personalUser.setUsername(utils.generatePublicId(20));
	    personalUser.setEmail(utils.generatePublicId(10));
	    personalUser.setUserId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	    personalUser.setEncryptedPassword(bCryptPasswordEncoder.encode(InitialConstants.INITIAL_PASSWORD));

	    List<RoleEntity> roles = new ArrayList<>();
	    for (String roleName : InitialConstants.PERSONAL_USER_ROLES) {
		RoleEntity role = roleRepository.findByName(roleName);
		if (role != null) {
		    roles.add(role);
		}
	    }

	    personalUser.setRoles(roles);

	    users.add(personalUser);

	    ProfileEntity profile = new ProfileEntity();
	    profile.setProfilePrivateId(utils.generatePublicId(AppConstants.PRIVATE_ID_LENGTH));
	    profile.setProfilePublicId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	    profile.setFirstName(utils.generatePublicId(10));
	    profile.setLastName(utils.generatePublicId(10));
	    profile.setContactEmail(personalUser.getEmail());
	    profile.setUserId(personalUser.getUserId());
	    profile.setShortIntro("This user is created to test pagination");
	    profile.setAbout(
		    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"");
	    profile.setLocationCity(utils.generatePublicId(10));
	    profile.setLocationCountry(utils.generatePublicId(10));
	    profiles.add(profile);
	    counter++;
	}

	userRepository.saveAll(users);
	profileRepository.saveAll(profiles);
    }
}
