package devsearch.users.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.ui.model.request.UserDetailsRequestModel;
import devsearch.users.ws.ui.model.response.UserResponse;
import devsearch.users.ws.ui.model.response.operations.OperationName;
import devsearch.users.ws.ui.model.response.operations.OperationResult;
import devsearch.users.ws.ui.model.response.operations.OperationStatusRest;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper modelMapper;

    @GetMapping(path = "/status")
    public String welcome() {
	return "UserController is working!";
    }

    @GetMapping(path = "/{id}")
    public UserResponse getUser(@PathVariable String id) throws UsersRestApiException {
	UserDto userDto = userService.getUserByPublicId(id);

	return modelMapper.map(userDto, UserResponse.class);
    }

    @GetMapping()
    public List<UserResponse> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
	    @RequestParam(value = "limit", defaultValue = "20") int limit) throws UsersRestApiException {
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

    @PostMapping
    public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws UsersRestApiException {
	UserDto userDto = modelMapper.map(userDetails, UserDto.class);
	UserDto createdUser = userService.createUser(userDto);

	return modelMapper.map(createdUser, UserResponse.class);
    }

    @PutMapping(path = "/{id}")
    public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails)
	    throws UsersRestApiException {
	UserDto userDto = modelMapper.map(userDetails, UserDto.class);

	UserDto updatedUser = userService.updateUser(id, userDto);

	return modelMapper.map(updatedUser, UserResponse.class);
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusRest deleteUser(@PathVariable String id) {
	OperationStatusRest returnValue = new OperationStatusRest();

	try {
	    userService.deleteUser(publicId);
	    returnValue.setOperationName(OperationName.DELETE.name());
	    returnValue.setOperationResult(OperationResult.SUCCESS.name());
	} catch (UsersRestApiException ex) {
	    returnValue.setOperationName(OperationName.DELETE.name());
	    returnValue.setOperationResult(OperationResult.ERROR.name());
	    returnValue.setExceptionMessage(ex.getMessage());
	}

	return returnValue;
    }
}
