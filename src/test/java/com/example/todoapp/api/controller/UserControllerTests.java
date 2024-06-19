package com.example.todoapp.api.controller;

import com.example.todoapp.api.controller.UserController;
import com.example.todoapp.api.model.User;
import com.example.todoapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        User newUser = new User("testUser", "testPassword", "test@example.com");

        when(userService.registerUser(any(User.class))).thenReturn("User registered successfully");

        ResponseEntity<String> response = userController.registerUser(newUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void testRegisterUser_Failure() {
        User existingUser = new User("existingUser", "existingPassword", "existing@example.com");

        when(userService.registerUser(any(User.class))).thenReturn("User already exists");
        ResponseEntity<String> response = userController.registerUser(existingUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    void testFetchUser_ExistingUser() {
        User existingUser = new User("testUser", "test@example.com", "password123");
        when(userService.fetchUser(anyString())).thenReturn(existingUser);

        ResponseEntity<?> response = userController.fetchUser("testUser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingUser, response.getBody());
    }

    @Test
    void testFetchUser_NonExistingUser() {
        when(userService.fetchUser(anyString())).thenReturn(null);

        ResponseEntity<?> response = userController.fetchUser("nonExistingUser");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testUpdateUser_Success() {
        String username = "testUser";
        User updatedUser = new User(username, "updated@example.com", "newPassword");

        when(userService.updateUser(anyString(), any(User.class))).thenReturn("User updated successfully");

        ResponseEntity<String> response = userController.updateUser(username, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        String username = "nonExistingUser";
        User updatedUser = new User(username, "updated@example.com", "newPassword");

        when(userService.updateUser(anyString(), any(User.class))).thenReturn("User not found");

        ResponseEntity<String> response = userController.updateUser(username, updatedUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testPartialUpdateUser_Success() {
        String username = "testUser";
        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "updated@example.com");

        when(userService.partialUpdateUser(anyString(), any(Map.class))).thenReturn("User updated successfully");
        ResponseEntity<String> response = userController.partialUpdateUser(username, updates);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());
    }

    @Test
    void testPartialUpdateUser_UserNotFound() {
        String username = "nonExistingUser";
        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "updated@example.com");

        when(userService.partialUpdateUser(anyString(), any(Map.class))).thenReturn("User not found");
        ResponseEntity<String> response = userController.partialUpdateUser(username, updates);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }
    @Test
    void testDeleteUser_Success() {
        String username = "testUser";
        User existingUser = new User(username, "test@example.com", "password123");
        when(userService.fetchUser(anyString())).thenReturn(existingUser);
        when(userService.deleteUser(anyString())).thenReturn("User deleted successfully");

        ResponseEntity<String> response = userController.deleteUser(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        String username = "nonExistingUser";
        when(userService.fetchUser(anyString())).thenReturn(null);

        ResponseEntity<String> response = userController.deleteUser(username);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

}


