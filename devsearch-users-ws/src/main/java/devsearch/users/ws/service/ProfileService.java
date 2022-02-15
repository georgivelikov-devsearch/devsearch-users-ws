package devsearch.users.ws.service;

import devsearch.users.ws.exception.UsersRestApiException;
import devsearch.users.ws.shared.dto.ProfileDto;

public interface ProfileService {

    public ProfileDto getProfileByProfileId(String profileId) throws UsersRestApiException;

    public ProfileDto getProfileByUserId(String userId) throws UsersRestApiException;

    public ProfileDto updateProfile(ProfileDto profileDto) throws UsersRestApiException;

    public ProfileDto createProfile(ProfileDto profileDto) throws UsersRestApiException;

    public void deleteProfile(String profileId) throws UsersRestApiException;
}
