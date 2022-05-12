package com.example.smile_ukraine.Modals;

import androidx.navigation.Navigator;

public class User {
    private String id;
    private String username;
    private String phone_number;
    private String bio;
    private String email;

    public User(String id, String username, String phone_number, String bio, String email) {
        this.id = id;
        this.username = username;
        this.phone_number = phone_number;
        this.bio = bio;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
