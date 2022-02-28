package devsearch.users.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.io.entity.ProfileEntity;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.ProfileRepository;
import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.service.ProfileService;
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
    private Mapper modelMapper;

    @Autowired
    private Utils utils;

    @Override
    public ProfileDto getProfileByProfilePrivateId(String profilePrivateId) throws RestApiUsersException {
	ProfileEntity profileEntity = profileRepository.findByProfilePrivateId(profilePrivateId);

	if (profileEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	return modelMapper.map(profileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto getProfileByProfilePublicId(String profilePublicId) throws RestApiUsersException {
	ProfileEntity profileEntity = profileRepository.findByProfilePublicId(profilePublicId);

	if (profileEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	return modelMapper.map(profileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto getProfileByUserId(String userId) throws RestApiUsersException {
	ProfileEntity profileEntity = profileRepository.findByUserId(userId);
	if (profileEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_PFOFILE_FOUND_FOR_THIS_USER);
	}

	return modelMapper.map(profileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto createProfile(ProfileDto profileDto) throws RestApiUsersException {
	String userId = profileDto.getUserId();
	UserEntity userEntity = userRepository.findByUserId(userId);
	if (userEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_USER_FOUND_FOR_THIS_USER_ID);
	}

	ProfileEntity profileEntity = profileRepository.findByUserId(userId);
	if (profileEntity != null) {
	    throw new RestApiUsersException(ExceptionMessages.PROFILE_ALREADY_EXISTS_FOR_THIS_USER);
	}

	profileDto.setProfilePrivateId(utils.generatePublicId(AppConstants.PRIVATE_ID_LENGTH));
	profileDto.setProfilePublicId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	profileDto.setUserId(userId);

	profileEntity = modelMapper.map(profileDto, ProfileEntity.class);

	ProfileEntity storedProfileEntity = null;
	try {
	    storedProfileEntity = profileRepository.save(profileEntity);
	} catch (Exception ex) {
	    throw new RestApiUsersException(ExceptionMessages.CREATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(storedProfileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto updateProfile(ProfileDto profileDto) throws RestApiUsersException {
	ProfileEntity profileEntity = profileRepository.findByProfilePrivateId(profileDto.getProfilePrivateId());
	if (profileEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	profileEntity.setFirstName(profileDto.getFirstName());
	profileEntity.setLastName(profileDto.getLastName());
	profileEntity.setShortIntro(profileDto.getShortIntro());
	profileEntity.setAbout(profileDto.getAbout());
	profileEntity.setContactEmail(profileEntity.getContactEmail());
	profileEntity.setSocialLinkedIn(profileDto.getSocialLinkedIn());
	profileEntity.setSocialTwitter(profileDto.getSocialTwitter());
	profileEntity.setSocialGithub(profileDto.getSocialGithub());
	profileEntity.setSocialYoutube(profileDto.getSocialYoutube());
	profileEntity.setSocialWebsite(profileDto.getSocialWebsite());
	profileEntity.setLocationCity(profileDto.getLocationCity());
	profileEntity.setLocationCountry(profileDto.getLocationCountry());
	profileEntity.setProfilePictureUrl(profileDto.getProfilePictureUrl());

	ProfileEntity updatedProfileEntity = null;
	try {
	    updatedProfileEntity = profileRepository.save(profileEntity);
	} catch (Exception ex) {
	    System.out.println(ex.getMessage());
	    throw new RestApiUsersException(ExceptionMessages.UPDATE_RECORD_FAILED, ex.getMessage());
	}

	return modelMapper.map(updatedProfileEntity, ProfileDto.class);
    }

    @Override
    public void deleteProfile(String profileId) throws RestApiUsersException {
	// Check if underlying user is deleted?
	ProfileEntity profileEntity = profileRepository.findByProfilePrivateId(profileId);
	if (profileEntity == null) {
	    throw new RestApiUsersException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	try {
	    profileRepository.delete(profileEntity);
	} catch (Exception ex) {
	    throw new RestApiUsersException(ExceptionMessages.DELETE_RECORD_FAILED, ex.getMessage());
	}
    }

}
