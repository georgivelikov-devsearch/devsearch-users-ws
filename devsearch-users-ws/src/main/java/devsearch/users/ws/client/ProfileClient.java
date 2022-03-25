package devsearch.users.ws.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import devsearch.users.ws.ui.model.request.DeveloperRequest;

@FeignClient("developers-ws")
public interface ProfileClient {

    @PostMapping("/developers/initial")
    public ResponseEntity<String> initialSeed(List<DeveloperRequest> request);
}
