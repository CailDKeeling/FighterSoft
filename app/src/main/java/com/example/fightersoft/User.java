package com.example.fightersoft;

/* This Class is for storing user information and sending the info over to Firebase */

public class User {

    // user information to send to firebase
    public String username, password, email;

    // default constructor
    public User() {



    }

    public User(String username, String password, String email) {

        this.username = username;
        this.password = password;
        this.email = email;

    }

}
