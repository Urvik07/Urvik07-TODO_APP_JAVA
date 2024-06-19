package com.example.todoapp.api.controller;

import com.example.todoapp.api.model.User;
import com.example.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String response = userService.registerUser(user);
        if (response.equals("User registered successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchUser(@RequestParam String username) {
        User user = userService.fetchUser(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestParam String username, @RequestBody User userUpdate) {
        String response = userService.updateUser(username, userUpdate);
        if (response.equals("User updated successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/updatePartial")
    public ResponseEntity<String> partialUpdateUser(@RequestParam String username, @RequestBody Map<String, Object> updates) {
        String response = userService.partialUpdateUser(username, updates);
        if (response.equals("User updated successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        User existingUser = userService.fetchUser(username);
        if (existingUser != null) {
            userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<User>> getAllUsers() {
        Collection<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}

