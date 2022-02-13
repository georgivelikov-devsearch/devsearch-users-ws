package devsearch.users.ws.service;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.shared.dto.ProfileDto;
import devsearch.users.ws.shared.dto.UserDto;

public interface ProfileService {

    public ProfileDto getProfileByProfileId(String profileId) throws UsersRestApiException;

    public ProfileDto getProfileByUser(UserDto userDto) throws UsersRestApiException;

    public ProfileDto getProfileByUserId(String userId) throws UsersRestApiException;

    public ProfileDto updateProfile(String profileId, ProfileDto profileDto) throws UsersRestApiException;

    public ProfileDto createProfile(ProfileDto profileDto) throws UsersRestApiException;

    public void deleteProfile(String profileId) throws UsersRestApiException;
}
