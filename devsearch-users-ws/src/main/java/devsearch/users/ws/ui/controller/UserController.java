package devsearch.users.ws.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.ui.model.request.UserDetailsRequestModel;
import devsearch.users.ws.ui.model.response.UserResponse;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(path = "/status")
    public String welcome() {
	return "UserController is working!";
    }

    @GetMapping(path = "/{publicId}")
    public UserResponse getUser(@PathVariable String userId) throws UsersRestApiException {
	UserDto userDto = userService.getUserByPublicId(userId);

	return modelMapper.map(userDto, UserResponse.class);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws UsersRestApiException {
	UserDto userDto = modelMapper.map(userDetails, UserDto.class);
	UserDto createdUser = userService.createUser(userDto);

	return modelMapper.map(createdUser, UserResponse.class);
    }
}
