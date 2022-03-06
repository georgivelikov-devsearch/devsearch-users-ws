package devsearch.users.ws.service;

import devsearch.users.ws.exception.RestApiUsersException;
import devsearch.users.ws.shared.dto.ProfileDto;
import devsearch.users.ws.shared.dto.ProfileListDto;

public interface ProfileService {

    public ProfileDto getProfileByProfilePrivateId(String profilePrivateId) throws RestApiUsersException;

    public ProfileDto getProfileByProfilePublicId(String profilePublicId) throws RestApiUsersException;

    public ProfileDto getProfileByUserId(String userId) throws RestApiUsersException;

    public ProfileDto createProfile(ProfileDto profileDto) throws RestApiUsersException;

    public ProfileDto updateProfile(ProfileDto profileDto) throws RestApiUsersException;

    public void deleteProfile(String profileId) throws RestApiUsersException;

    public ProfileListDto getPublicProfiles(int page, int limit) throws RestApiUsersException;
}
