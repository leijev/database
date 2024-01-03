package com.example.springjwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProjectController {
    private UserService userService = new UserService();
    private Logger logger = Logger.getAnonymousLogger();

    @GetMapping("/time")
    public long test() {
        logger.info("new user checkout");
        return Instant.now().toEpochMilli();
    }

    @GetMapping("/bestUsers")
    public List<User> getBestUser() {
        return userService.sortUsers();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRemoteAddr();
    }
}
