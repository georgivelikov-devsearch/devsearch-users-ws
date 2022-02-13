package devsearch.users.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.io.entity.ProfileEntity;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.ProfileRepository;
import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.service.ProfileService;
import devsearch.users.ws.service.UserService;
import devsearch.users.ws.shared.dto.ProfileDto;
import devsearch.users.ws.shared.utils.AppConstants;
import devsearch.users.ws.shared.utils.Mapper;
import devsearch.users.ws.shared.utils.Utils;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper modelMapper;

    @Autowired
    private Utils utils;

    @Override
    public ProfileDto getProfileByProfileId(String profileId) throws UsersRestApiException {
	ProfileEntity profileEntity = profileRepository.findByProfileId(profileId);

	if (profileEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	return modelMapper.map(profileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto getProfileByUserId(String userId) throws UsersRestApiException {
	UserEntity userEntity = userRepository.findByUserId(userId);

	ProfileEntity profileEntity = profileRepository.findByUser(userEntity);

	if (profileEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_PFOFILE_FOUND_FOR_THIS_USER);
	}

	return modelMapper.map(profileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto updateProfile(String profileId, ProfileDto profileDto) throws UsersRestApiException {
	ProfileEntity profileEntity = profileRepository.findByProfileId(profileId);
	if (profileEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	profileEntity.setDisplayName(profileDto.getDisplayName());
	profileEntity.setShortIntro(profileDto.getShortIntro());
	profileEntity.setBio(profileDto.getBio());
	profileEntity.setSocialLinkedIn(profileDto.getSocialLinkedIn());
	profileEntity.setSocialTwitter(profileDto.getSocialTwitter());
	profileEntity.setSocialGithub(profileDto.getSocialGithub());
	profileEntity.setSocialYoutube(profileDto.getSocialYoutube());
	profileEntity.setSocialWebsite(profileDto.getSocialWebsite());

	userService.updateUser(profileEntity.getUser().getUserId(), profileDto.getUser());

	ProfileEntity updatedProfileEntity = null;
	try {
	    updatedProfileEntity = profileRepository.save(profileEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.UPDATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(updatedProfileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto createProfile(ProfileDto profileDto) throws UsersRestApiException {
	ProfileEntity profileEntity = modelMapper.map(profileDto, ProfileEntity.class);

	profileEntity.setProfileId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));

	ProfileEntity storedProfileEntity = null;
	try {
	    storedProfileEntity = profileRepository.save(profileEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.CREATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(storedProfileEntity, ProfileDto.class);
    }

    @Override
    public void deleteProfile(String profileId) throws UsersRestApiException {
	// Check if underlying user is deleted?
	ProfileEntity profileEntity = profileRepository.findByProfileId(profileId);
	if (profileEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	try {
	    profileRepository.delete(profileEntity);
	} catch (Exception ex) {
	    throw new UsersRestApiException(ExceptionMessages.DELETE_RECORD_FAILED, ex.getMessage());
	}
    }

}
