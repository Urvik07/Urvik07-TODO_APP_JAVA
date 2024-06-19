package com.example.todoapp.service;

import com.example.todoapp.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private Map<String, User> userStore = new HashMap<>();

    public Map<String, User> getUserStore() {
        return userStore;
    }

    public void setUserStore(Map<String, User> userStore) {
        this.userStore = userStore;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String registerUser(User user) {
        if (userStore.containsKey(user.getUsername())) {
            return "User already exists";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userStore.put(user.getUsername(), user);
        return "User registered successfully";
    }

    public User fetchUser(String username) {
        return userStore.get(username);
    }

    public String updateUser(String username, User updatedUser) {
        if (userStore.containsKey(username)) {
            User existingUser = userStore.get(username);
            if (!updatedUser.getPassword().equals(existingUser.getPassword()) &&!updatedUser.getPassword().isEmpty()) {
                updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
            }
            existingUser.setEmail(updatedUser.getEmail());

            userStore.put(username, existingUser);
            return "User updated successfully";
        } else {
            return "User not found";
        }
    }

    public String partialUpdateUser(String username, Map<String, Object> updates) {
        if (userStore.containsKey(username)) {
            User user = userStore.get(username);

            updates.forEach((key, value) -> {
                switch (key) {
                    case "email":
                        user.setEmail((String) value);
                        break;
                    case "password":
                        user.setPassword(bCryptPasswordEncoder.encode((String) value));
                        break;
                    default:
                        break;
                }
            });

            userStore.put(username, user);
            return "User updated successfully";
        } else {
            return "User not found";
        }
    }
    public String deleteUser(String username) {
        if (userStore.containsKey(username)) {
            userStore.remove(username);
            return "User deleted successfully";
        } else {
            return "User not found";
        }
    }

    public Collection<User> getAllUsers() {
        return userStore.values();
    }
}
