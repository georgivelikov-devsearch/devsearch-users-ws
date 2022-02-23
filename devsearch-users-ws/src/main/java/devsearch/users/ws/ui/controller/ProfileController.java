package devsearch.users.ws.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.service.ProfileService;
import devsearch.users.ws.shared.dto.ProfileDto;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.ui.model.request.ProfileRequest;
import devsearch.users.ws.ui.model.response.ProfilePrivateResponse;

@RestController
@RequestMapping("profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private Mapper modelMapper;

    @GetMapping(path = "/status")
    public String status() {
	return "ProfileController is working!";
    }

    @GetMapping(path = "/user/{userId}")
    public ProfilePrivateResponse getProfileByUserId(@PathVariable String userId) throws RestApiUsersException {
	ProfileDto profileDto = profileService.getProfileByUserId(userId);

	return modelMapper.map(profileDto, ProfilePrivateResponse.class);
    }

    @GetMapping(path = "/private/{profilePrivateId}")
    public ProfilePrivateResponse getProfileByPrivateId(@PathVariable String profilePrivateId)
	    throws RestApiUsersException {
	ProfileDto profileDto = profileService.getProfileByProfilePrivateId(profilePrivateId);

	return modelMapper.map(profileDto, ProfilePrivateResponse.class);
    }

    @PostMapping
    public ProfilePrivateResponse createProfile(@RequestBody ProfileRequest profileRequest)
	    throws RestApiUsersException {
	ProfileDto profileDto = modelMapper.map(profileRequest, ProfileDto.class);
	ProfileDto createdProfile = profileService.createProfile(profileDto);

	return modelMapper.map(createdProfile, ProfilePrivateResponse.class);
    }

    @PutMapping
    public ProfilePrivateResponse updateProfile(@RequestBody ProfileRequest profileRequest)
	    throws RestApiUsersException {
	ProfileDto profileDto = modelMapper.map(profileRequest, ProfileDto.class);
	ProfileDto updatedProfile = profileService.updateProfile(profileDto);

	return modelMapper.map(updatedProfile, ProfilePrivateResponse.class);
    }
}
