package devsearch.users.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devsearch.users.ws.client.DeveloperClient;
import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.ui.model.request.UserRequest;
import devsearch.users.ws.ui.model.response.UserResponse;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private DeveloperClient profileClient;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper modelMapper;

    @GetMapping(path = "/status")
    public String status() {
	return "UserController is working!";
    }

    @GetMapping(path = "/{userId}")
    public UserResponse getUser(@PathVariable String userId) throws RestApiUsersException {
	UserDto userDto = userService.getUserByUserId(userId);

	return modelMapper.map(userDto, UserResponse.class);
    }

    @GetMapping()
    public List<UserResponse> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
	    @RequestParam(value = "limit", defaultValue = "20") int limit) throws RestApiUsersException {
	List<UserResponse> returnValue = new ArrayList<>();

	// In the Repository implementation pagination starts with '0', but in UI
	// usually pages start from 1, 2, 3 etc. So UI will send the number of the page,
	// which should be reduced by 1
	if (page > 0) {
	    page -= 1;
	}

	List<UserDto> users = userService.getUsers(page, limit);

	for (UserDto userDto : users) {
	    UserResponse userRest = modelMapper.map(userDto, UserResponse.class);
	    returnValue.add(userRest);
	}

	return returnValue;
    }

//    @PostMapping
//    public RegisterResponse createUser(@RequestBody RegisterRequest registerRequest) throws RestApiUsersException {
//	UserDto userDto = modelMapper.map(registerRequest, UserDto.class);
//	UserDto createdUser = userService.createUser(userDto);
//
//	// Get firstName and lastName from request
//	DeveloperRequest profileRequest = modelMapper.map(registerRequest, DeveloperRequest.class);
//	// Set userId to that profile
//	profileRequest.setUserId(createdUser.getUserId());
//	profileRequest.setUsername(createdUser.getUsername());
//
//	// Create new profile for the new user
////	RestTemplate example	
////	ProfileDto createdProfile = profileService.createProfile(profileDto);
////	RestTemplate restTemplate = new RestTemplate();
////	HttpEntity<ProfileDto> request = new HttpEntity<>(profileDto);
////	ProfilePrivateResponse createdProfile = restTemplate.postForObject("http://localhost:8080/profiles", request,
////		ProfilePrivateResponse.class);
//
//	ResponseEntity<ProfilePrivateResponse> createdProfileResponse = profileClient.createProfile(profileRequest);
//	ProfilePrivateResponse createdProfile = createdProfileResponse.getBody();
//
//	RegisterResponse response = new RegisterResponse();
//	response.setUsername(createdUser.getUsername());
//	response.setEmail(createdUser.getEmail());
//	response.setUserId(createdUser.getUserId());
//	response.setFirstName(createdProfile.getFirstName());
//	response.setLastName(createdProfile.getLastName());
//
//	return response;
//    }

    @PutMapping()
    public UserResponse updateUser(@RequestBody UserRequest userRequest) throws RestApiUsersException {
	UserDto userDto = modelMapper.map(userRequest, UserDto.class);

	UserDto updatedUser = userService.updateUser(userDto);

	return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) throws RestApiUsersException {
	userService.deleteUser(userId);

	return new ResponseEntity<String>(userId, HttpStatus.OK);
    }
}
