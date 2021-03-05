package com.minorproject.tourist.guide.service;

import java.util.List;

import com.minorproject.tourist.guide.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    List<User> findAll();

    User findById(int theId);

    void deleteById(int theId);

}
