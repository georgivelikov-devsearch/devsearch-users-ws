package devsearch.users.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

    public UserDto getUserByUserId(String userId) throws UsersRestApiException;

    public UserDto getUserByUsername(String username) throws UsersRestApiException;

    public UserDto getUserByEmail(String email) throws UsersRestApiException;

    public UserDto getUserForLogin(String username) throws UsersRestApiException;

    public UserDto updateUser(UserDto userDto) throws UsersRestApiException;

    public UserDto createUser(UserDto userDto) throws UsersRestApiException;

    public void deleteUser(String userId) throws UsersRestApiException;

    public List<UserDto> getUsers(int page, int limit) throws UsersRestApiException;
}
