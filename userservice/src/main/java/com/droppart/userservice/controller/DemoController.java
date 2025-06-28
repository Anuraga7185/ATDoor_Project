package com.droppart.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/secure")
    public ResponseEntity<String> securedEndPoint(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        String username = switch (principal) {
            case org.springframework.security.core.userdetails.User user -> user.getUsername();
            case com.droppart.userservice.model.User user -> user.getUsername();
            case String s -> s;
            default -> principal.toString();
        };

        return ResponseEntity.ok(username + "Secured End Point");
    }

}
