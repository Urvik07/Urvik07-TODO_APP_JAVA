package com.example.todoapp.service;

import com.example.todoapp.api.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private Map<String, User> userStore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userStore = new HashMap<>();
    }


    @Test
    void testRegisterUser_Success() {
        User newUser = new User("testUser", "test@example.com", "password123");

        when(bCryptPasswordEncoder.encode("password123")).thenReturn("encodedPassword");

        String response = userService.registerUser(newUser);

        assertEquals("User registered successfully", response);
        User fetchedUser = userService.fetchUser("testUser");
        assertNotNull(fetchedUser);
        assertEquals("encodedPassword", fetchedUser.getPassword());
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        User existingUser = new User("existingUser", "existing@example.com", "existingPassword");
        userStore.put(existingUser.getUsername(), existingUser);

        when(userService.registerUser(existingUser)).thenReturn("User already exists");

        String response = userService.registerUser(existingUser);

        assertEquals("User already exists", response);
        assertEquals(1, userStore.size());
    }

    @Test
    void testFetchUser_ExistingUser() {
        User existingUser = new User("testUser", "test@example.com", "password123");
        userService.getUserStore().put(existingUser.getUsername(), existingUser);

        User fetchedUser = userService.fetchUser("testUser");

        assertNotNull(fetchedUser);
        assertEquals(existingUser, fetchedUser);
    }

    @Test
    void testFetchUser_NonExistingUser() {
        User fetchedUser = userService.fetchUser("nonExistingUser");

        assertNull(fetchedUser);
    }

    @Test
    void testUpdateUser_Success() {
        User existingUser = new User("testUser", "test@example.com", "password123");
        userService.getUserStore().put(existingUser.getUsername(), existingUser);

        User updatedUser = new User("testUser", "updated@example.com", "newPassword");

        when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");

        String response = userService.updateUser("testUser", updatedUser);

        assertEquals("User updated successfully", response);
        assertEquals("updated@example.com", existingUser.getEmail());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User updatedUser = new User("nonExistingUser", "updated@example.com", "newPassword");

        String response = userService.updateUser("nonExistingUser", updatedUser);

        assertEquals("User not found", response);
    }

    @Test
    void testPartialUpdateUser_Success_email() {
        User existingUser = new User("testUser", "test@example.com", "password123");
        userService.getUserStore().put(existingUser.getUsername(), existingUser);

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "updated@example.com");

        when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");

        String response = userService.partialUpdateUser("testUser", updates);

        assertEquals("User updated successfully", response);
        assertEquals("updated@example.com", existingUser.getEmail());
    }


    @Test
    void testPartialUpdateUser_Success_password() {
        User existingUser = new User("testUser", "test@example.com", "password123");

        userService.getUserStore().put(existingUser.getUsername(), existingUser);

        Map<String, Object> updates = new HashMap<>();
        updates.put("password", "newPassword123");

        when(bCryptPasswordEncoder.encode("newPassword123")).thenReturn("encodedNewPassword");

        String response = userService.partialUpdateUser("testUser", updates);

        assertEquals("User updated successfully", response);
        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    void testPartialUpdateUser_UserNotFound() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "updated@example.com");

        String response = userService.partialUpdateUser("nonExistingUser", updates);

        assertEquals("User not found", response);
    }

    @Test
    void testDeleteUser_Success() {
        User existingUser = new User("testUser", "test@example.com", "password123");
        userService.getUserStore().put(existingUser.getUsername(), existingUser);

        String response = userService.deleteUser("testUser");

        assertEquals("User deleted successfully", response);
        assertEquals(0, userStore.size());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        String response = userService.deleteUser("nonExistingUser");

        assertEquals("User not found", response);
    }
}

