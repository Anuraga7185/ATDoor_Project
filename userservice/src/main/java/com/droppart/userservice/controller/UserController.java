package com.droppart.userservice.controller;

import com.droppart.userservice.model.User;
import com.droppart.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/by-id")
    public ResponseEntity<User> getUserById(@RequestParam("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/self-detail")
    public ResponseEntity<User> getUserByEmail(@RequestParam("username") String username) {
        User userDetail = userService.getUserByUserName(username);
        if (userDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDetail);
    }

    @GetMapping("/delivery-partners")
    public ResponseEntity<List<User>> getAllDeliveryPartners() {
        return ResponseEntity.ok(userService.getAllDeliveryPartners());
    }
}
