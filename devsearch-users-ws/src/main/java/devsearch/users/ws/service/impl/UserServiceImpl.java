package devsearch.users.ws.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.shared.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

    // @Autowired
    // BCryptPasswordEncoder bCryptpasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDto getUserByPublicId(String publicId) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByPublicId(publicId);

	if (userEntity == null) {
	    throw new UsersRestApiException();
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUsername(String username) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByUsername(username);

	if (userEntity == null) {
	    throw new UsersRestApiException();
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByEmail(email);

	if (userEntity == null) {
	    throw new UsersRestApiException();
	}

	return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(String publicId, UserDto userDto) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws UsersRestApiException {
	if (userRepository.findByEmail(userDto.getEmail()) != null) {
	    throw new UsersRestApiException();
	}

//	for (int i = 0; i < userDto.getAddresses().size(); i++) {
//	    AddressDto addressDto = userDto.getAddresses().get(i);
//	    addressDto.setUserDetails(userDto);
//	    addressDto.setAddressId(utils.generatePublicId(30));
//	    userDto.getAddresses().set(i, addressDto);
//	}

	UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

	userEntity.setPublicId(Utils.generatePublicId(30));
	userEntity.setEncryptedPassword(userDto.getPassword());
	// userEntity.setEncryptedPassword(bCryptpasswordEncoder.encode(userDto.getPassword()));

	UserEntity storedUserEntity = null;
	try {
	    storedUserEntity = userRepository.save(userEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException();
	}

	return modelMapper.map(storedUserEntity, UserDto.class);
    }

    @Override
    public void deleteUser(String publicId) throws UsersRestApiException {
	// TODO Auto-generated method stub

    }

    @Override
    public List<UserDto> getUsers(int page, int limit) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

}
