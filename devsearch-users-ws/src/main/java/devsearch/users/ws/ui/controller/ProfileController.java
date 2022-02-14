package devsearch.users.ws.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.service.ProfileService;
import devsearch.users.ws.shared.dto.ProfileDto;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.ui.model.response.ProfileResponse;

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

    @GetMapping(path = "/{profileId}")
    public ProfileResponse getUser(@PathVariable String profileId) throws UsersRestApiException {
	ProfileDto profileDto = profileService.getProfileByProfileId(profileId);

	return modelMapper.map(profileDto, ProfileResponse.class);
    }

}
