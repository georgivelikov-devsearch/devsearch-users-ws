package devsearch.users.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.shared.utils.Constants;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.shared.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

//    @Autowired
//    BCryptPasswordEncoder bCryptpasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper modelMapper;

    @Override
    public UserDto getUserByPublicId(String publicId) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByPublicId(publicId);

	if (userEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUsername(String username) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByUsername(username);

	if (userEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_USERNAME);
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByEmail(email);

	if (userEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_EMAIL);
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) throws UsersRestApiException {
	if (userRepository.findByUsername(userDto.getUsername()) != null) {
	    throw new UsersRestApiException(ExceptionMessages.RECORD_ALREADY_EXISTS_WITH_THIS_USERNAME);
	}

	if (userRepository.findByEmail(userDto.getEmail()) != null) {
	    throw new UsersRestApiException(ExceptionMessages.RECORD_ALREADY_EXISTS_WITH_THIS_EMAIL);
	}

//	for (int i = 0; i < userDto.getAddresses().size(); i++) {
//	    AddressDto addressDto = userDto.getAddresses().get(i);
//	    addressDto.setUserDetails(userDto);
//	    addressDto.setAddressId(utils.generatePublicId(30));
//	    userDto.getAddresses().set(i, addressDto);
//	}

	UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

	userEntity.setPublicId(Utils.generatePublicId(Constants.PUBLIC_ID_LENGTH));
	userEntity.setEncryptedPassword(userDto.getPassword());
	// userEntity.setEncryptedPassword(bCryptpasswordEncoder.encode(userDto.getPassword()));

	UserEntity storedUserEntity = null;
	try {
	    storedUserEntity = userRepository.save(userEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.INVALID_FIELD, ex.getMessage());
	}

	return modelMapper.map(storedUserEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(String publicId, UserDto userDto) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByPublicId(publicId);
	if (userEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	userEntity.setUsername(userDto.getUsername());
	userEntity.setFirstName(userDto.getFirstName());
	userEntity.setLastName(userDto.getLastName());
	userEntity.setEmail(userDto.getEmail());

	UserEntity updatedUserEntity = null;
	try {
	    updatedUserEntity = userRepository.save(userEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.INVALID_FIELD, ex.getMessage());
	}

	return modelMapper.map(updatedUserEntity, UserDto.class);
    }

    @Override
    public void deleteUser(String publicId) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByPublicId(publicId);
	if (userEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) throws UsersRestApiException {
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
