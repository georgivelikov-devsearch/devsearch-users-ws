package devsearch.users.ws;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialConstants {

    // STARTING AUTHORITIES
    static final String READ_AUTHORITY = "READ_AUTHORITY";
    static final String WRITE_AUTHORITY = "WRITE_AUTHORITY";
    static final String DELETE_AUTHORITY = "DELETE_AUTHORITY";

    static final List<String> AUTHORITIES = Arrays.asList(READ_AUTHORITY, WRITE_AUTHORITY, DELETE_AUTHORITY);

    // STARTING ROLES
    static final String ROLE_ADMIN = "ROLE_ADMIN";
    static final List<String> ADMIN_AUTHORITIES = Arrays.asList(READ_AUTHORITY, WRITE_AUTHORITY, DELETE_AUTHORITY);

    static final String ROLE_USER = "ROLE_USER";
    static final List<String> USER_AUTHORITIES = Arrays.asList(READ_AUTHORITY, WRITE_AUTHORITY);

    static final List<String> ROLES = Arrays.asList(ROLE_ADMIN, ROLE_USER);

    static final Map<String, List<String>> ROLE_MAP = new HashMap<>() {
	private static final long serialVersionUID = -2648851767118336783L;
	{
	    put(ROLE_ADMIN, ADMIN_AUTHORITIES);
	    put(ROLE_USER, USER_AUTHORITIES);
	}
    };

    // "Administrator" user
    static final String INITIAL_SETUP_CONFIG = "initialSetupConfigured";
    static final String USERNAME = "Administrator";
    static final String FIRST_NAME = "Administrator";
    static final String LAST_NAME = "Administrator";
    static final String INITIAL_PASSWORD = "abcd1234";
    static final List<String> ADMINISTRATOR_USER_ROLES = Arrays.asList(ROLE_ADMIN, ROLE_USER);
}
