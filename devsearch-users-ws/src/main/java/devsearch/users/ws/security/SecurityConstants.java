package devsearch.users.ws.security;

public class SecurityConstants {

    public static final String TOKEN_SECRET_KEY = "token.secret";
    public static final String TOKEN_EXPIRATION_KEY = "token.expiration.time"; // 10 days
    public static final String TOKEN_PREFIX_KEY = "token.prefix";
    public static final String USER_ID_HEADER_KEY = "header.user.id";
    public static final String STATUS_URL = "/users/status";
    public static final String SIGN_UP_URL = "/users";
    public static final String LOGIN_URL = "/users/login";
}
