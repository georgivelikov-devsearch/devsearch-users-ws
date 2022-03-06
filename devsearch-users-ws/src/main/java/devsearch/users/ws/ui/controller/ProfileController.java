package devsearch.users.ws.ui.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.service.ImageService;
import devsearch.users.ws.service.ProfileService;
import devsearch.users.ws.shared.dto.ProfileDto;
import devsearch.users.ws.shared.dto.ProfileListDto;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.ui.model.request.ProfileRequest;
import devsearch.users.ws.ui.model.response.ProfilePrivateResponse;
import devsearch.users.ws.ui.model.response.ProfilePublicListResponse;
import devsearch.users.ws.ui.model.response.ProfilePublicResponse;

@RestController
@RequestMapping("profiles")
public class ProfileController {

    @Autowired
    private ImageService imageService;

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

    @GetMapping(path = "/public/{profilePublicId}")
    public ProfilePublicResponse getProfileByPublicId(@PathVariable String profilePublicId)
	    throws RestApiUsersException {
	ProfileDto profileDto = profileService.getProfileByProfilePublicId(profilePublicId);

	return modelMapper.map(profileDto, ProfilePublicResponse.class);
    }

    @GetMapping(path = "/public")
    public ProfilePublicListResponse getPublicProfiles(@RequestParam(value = "page", defaultValue = "1") int page,
	    @RequestParam(value = "limit", defaultValue = "6") int limit,
	    @RequestParam(value = "userId", defaultValue = "") String userId) throws RestApiUsersException {

	// In the Repository implementation pagination starts with '0', but in UI
	// usually pages start from 1, 2, 3 etc. So UI will send the number of the page,
	// which should be reduced by 1
	if (page > 0) {
	    page -= 1;
	}

	ProfileListDto profiles = profileService.getPublicProfiles(page, limit);
	Collection<ProfilePublicResponse> responseProfiles = new ArrayList<ProfilePublicResponse>();
	boolean senderFound = false;
	for (ProfileDto profile : profiles.getProfiles()) {
	    ProfilePublicResponse publicProfile = modelMapper.map(profile, ProfilePublicResponse.class);
	    if (!senderFound && profile.getUserId().equals(userId)) {
		publicProfile.setSender(true);
		senderFound = true;
	    }

	    responseProfiles.add(publicProfile);
	}

	ProfilePublicListResponse response = new ProfilePublicListResponse();

	response.setPage(page);
	response.setTotalPages(profiles.getTotalPages());
	response.setProfiles(responseProfiles);

	return response;
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
	if (profileDto.isNewProfilePictureUpload()) {
	    String profilePictureUrl = imageService.saveImageAndReturnURL(profileDto.getProfilePictureBase64(),
		    profileDto.getProfilePrivateId());
	    profileDto.setProfilePictureUrl(profilePictureUrl);
	}

	ProfileDto updatedProfile = profileService.updateProfile(profileDto);

	return modelMapper.map(updatedProfile, ProfilePrivateResponse.class);
    }
}
