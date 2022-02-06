package devsearch.users.ws.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping(path = "/status")
    public String welcome() {
	return "UserController is working!";
    }
}
