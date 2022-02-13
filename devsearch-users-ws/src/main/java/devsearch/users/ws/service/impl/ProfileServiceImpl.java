package devsearch.users.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.io.repository.ProfileRepository;
import devsearch.users.ws.service.ProfileService;
import devsearch.users.ws.shared.dto.ProfileDto;
import devsearch.users.ws.shared.dto.UserDto;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.shared.utils.Utils;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Mapper modelMapper;

    @Autowired
    private Utils utils;

    @Override
    public ProfileDto getProfileByProfileId(String profileId) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ProfileDto getProfileByUser(UserDto userDto) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ProfileDto getProfileByUserId(String userId) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ProfileDto updateProfile(String profileId, ProfileDto profileDto) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ProfileDto createProfile(ProfileDto profileDto) throws UsersRestApiException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteProfile(String profileId) throws UsersRestApiException {
	// TODO Auto-generated method stub

    }

}
