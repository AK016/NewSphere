package com.masai.services;


import com.masai.entities.User;
import com.masai.exceptions.InvalidDetailsException;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<User> users;

    public CustomerService() {
        this.users = new ArrayList<>();
    }

    public void signup(User user) throws InvalidDetailsException {
        validateUsername(user.getUsername());
        users.add(user);
        System.out.println("User registered successfully!");
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void validateUsername(String username) throws InvalidDetailsException {
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username)) {
                throw new InvalidDetailsException("Username already exists. Please choose a different username.");
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
