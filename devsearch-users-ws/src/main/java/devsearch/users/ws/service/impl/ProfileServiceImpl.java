package devsearch.users.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsearch.users.ws.exception.ExceptionMessages;
import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.io.entity.AddressEntity;
import devsearch.users.ws.io.entity.ProfileEntity;
import devsearch.users.ws.io.entity.UserEntity;
import devsearch.users.ws.io.repository.ProfileRepository;
import devsearch.users.ws.io.repository.UserRepository;
import devsearch.users.ws.service.ProfileService;
import devsearch.users.ws.shared.dto.AddressDto;
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
    public ProfileDto getProfileByProfileId(String profileId) throws UsersRestApiException {
	ProfileEntity profileEntity = profileRepository.findByProfileId(profileId);

	if (profileEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_RECORD_FOUND_WITH_THIS_ID);
	}

	return modelMapper.map(profileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto getProfileByUserId(String userId) throws UsersRestApiException {
	ProfileEntity profileEntity = profileRepository.findByUserId(userId);
	if (profileEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_PFOFILE_FOUND_FOR_THIS_USER);
	}

	return modelMapper.map(profileEntity, ProfileDto.class);
    }

    @Override
    public ProfileDto updateProfile(ProfileDto profileDto) throws UsersRestApiException {
	ProfileEntity profileEntity = profileRepository.findByProfileId(profileDto.getProfileId());
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

	profileEntity.getUser().setFirstName(profileDto.getUser().getFirstName());
	profileEntity.getUser().setLastName(profileDto.getUser().getLastName());

	for (AddressEntity address : profileEntity.getAddresses()) {
	    AddressDto addressDto = profileDto.getAddresses()
		    .stream()
		    .filter(currentAddressDto -> address.getAddressId().equals(currentAddressDto.getAddressId()))
		    .findFirst()
		    .orElse(null);
	    if (addressDto != null) {
		address.setCity(addressDto.getCity());
		address.setCountry(addressDto.getCountry());
		address.setPostalCode(addressDto.getPostalCode());
		address.setStreet(addressDto.getStreet());
		address.setType(addressDto.getType());
	    }
	}

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
	String userId = profileDto.getUser().getUserId();
	UserEntity userEntity = userRepository.findByUserId(userId);
	if (userEntity == null) {
	    throw new UsersRestApiException(ExceptionMessages.NO_USER_FOUND_FOR_THIS_PROFILE);
	}

	if (profileDto.getAddresses() != null) {
	    List<AddressDto> addressesDto = new ArrayList<>(profileDto.getAddresses());
	    for (int i = 0; i < addressesDto.size(); i++) {
		AddressDto addressDto = addressesDto.get(i);
		addressDto.setProfileDto(profileDto);
		addressDto.setAddressId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
		addressesDto.set(i, addressDto);
	    }
	}

	ProfileEntity profileEntity = profileRepository.findByUserId(userId);
	if (profileEntity != null) {
	    throw new UsersRestApiException(ExceptionMessages.PROFILE_ALREADY_EXISTS_FOR_THIS_USER);
	}

	profileDto.setProfileId(utils.generatePublicId(AppConstants.PUBLIC_ID_LENGTH));
	profileEntity = modelMapper.map(profileDto, ProfileEntity.class);
	// modelMapper creates new UserEntity from UserDao in ProfileDao. But we want to
	// use the already created user from the Registration process
	profileEntity.setUser(userEntity);
	userEntity.setProfile(profileEntity);

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
