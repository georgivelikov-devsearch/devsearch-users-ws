package devsearch.users.ws.io.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import devsearch.users.ws.ui.model.request.ProfileRequest;
import devsearch.users.ws.ui.model.response.ProfilePrivateResponse;

@FeignClient(name = "Profiles", url = "http://localhost:8080")
public interface ProfilesClient {

    @PostMapping("/profiles")
    public ProfilePrivateResponse createProfile(ProfileRequest request);
}
