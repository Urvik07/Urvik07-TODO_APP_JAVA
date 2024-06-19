package com.example.todoapp.api.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
public class User {
    private String username;
    @Email
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    public User(@JsonProperty("username") String username,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
