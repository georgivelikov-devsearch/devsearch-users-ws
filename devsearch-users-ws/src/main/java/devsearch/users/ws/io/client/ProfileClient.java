package devsearch.users.ws.io.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import devsearch.users.ws.ui.model.request.ProfileRequest;
import devsearch.users.ws.ui.model.response.ProfilePrivateResponse;

@FeignClient("profiles-ws")
public interface ProfileClient {

    @PostMapping("/profiles")
    public ResponseEntity<ProfilePrivateResponse> createProfile(ProfileRequest request);

    @PostMapping("/profiles/initial")
    public ResponseEntity<String> initialSeed(List<ProfileRequest> request);
}
