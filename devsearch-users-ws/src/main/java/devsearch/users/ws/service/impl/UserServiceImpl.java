package devsearch.users.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.security.UserPrincipal;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.shared.utils.AppConstants;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.shared.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptpasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper modelMapper;

    @Autowired
    private Utils utils;

    /*
     * This method comes from UserDetailsService interface and is used on Login
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	UserEntity userEntity = userRepository.findByUsername(username);

	if (userEntity == null) {
	    throw new UsernameNotFoundException(username);
	}

	return new UserPrincipal(userEntity);
    }

    @Override
    public UserDto getUserForLogin(String username) throws RestApiUsersException {
	UserEntity userEntity = userRepository.findByUsername(username);
	if (userEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_USERNAME);
	}

	UserDto userDto = new UserDto();
	BeanUtils.copyProperties(userEntity, userDto);
	return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws RestApiUsersException {
	UserEntity userEntity = userRepository.findByUserId(userId);

	if (userEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUsername(String username) throws RestApiUsersException {
	UserEntity userEntity = userRepository.findByUsername(username);

	if (userEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_USERNAME);
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) throws RestApiUsersException {
	UserEntity userEntity = userRepository.findByEmail(email);

	if (userEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_EMAIL);
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) throws RestApiUsersException {
	if (userRepository.findByUsername(userDto.getUsername()) != null) {
	    throw new RestApiUsersException(ExceptionMessages.RECORD_ALREADY_EXISTS_WITH_THIS_USERNAME);
	}

	if (userRepository.findByEmail(userDto.getEmail()) != null) {
	    throw new RestApiUsersException(ExceptionMessages.RECORD_ALREADY_EXISTS_WITH_THIS_EMAIL);
	}

	UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

	userEntity.setUserId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	userEntity.setEncryptedPassword(bCryptpasswordEncoder.encode(userDto.getPassword()));

	UserEntity storedUserEntity = null;
	try {
	    storedUserEntity = userRepository.save(userEntity);
	} catch (Exception ex) {
	    throw new RestApiUsersException(ExceptionMessages.CREATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(storedUserEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws RestApiUsersException {
	UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
	if (userEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	userEntity.setUsername(userDto.getUsername());
	userEntity.setEmail(userDto.getEmail());

	UserEntity updatedUserEntity = null;
	try {
	    updatedUserEntity = userRepository.save(userEntity);
	} catch (Exception ex) {
	    throw new RestApiUsersException(ExceptionMessages.UPDATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(updatedUserEntity, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) throws RestApiUsersException {
	UserEntity userEntity = userRepository.findByUserId(userId);
	if (userEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	try {
	    userRepository.delete(userEntity);
	} catch (Exception ex) {
	    throw new RestApiUsersException(ExceptionMessages.DELETE_RECORD_FAILED, ex.getMessage());
	}
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) throws RestApiUsersException {
	List<UserDto> returnValue = new ArrayList<>();
	Pageable pageableRequest = PageRequest.of(page, limit);
	Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
	List<UserEntity> users = userPage.getContent();

	for (UserEntity userEntity : users) {
	    UserDto userDto = modelMapper.map(userEntity, UserDto.class);
	    returnValue.add(userDto);
	}

	return returnValue;
    }
}
