package devsearch.users.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

    public UserDto getUserByUserId(String userId) throws RestApiUsersException;

    public UserDto getUserByUsername(String username) throws RestApiUsersException;

    public UserDto getUserByEmail(String email) throws RestApiUsersException;

    public UserDto getUserForLogin(String username) throws RestApiUsersException;

    public UserDto createUser(UserDto userDto) throws RestApiUsersException;

    public UserDto updateUser(UserDto userDto) throws RestApiUsersException;

    public void deleteUser(String userId) throws RestApiUsersException;

    public List<UserDto> getUsers(int page, int limit) throws RestApiUsersException;
}
