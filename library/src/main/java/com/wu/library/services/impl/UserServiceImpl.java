package com.wu.library.services.impl;

import com.wu.library.models.User;
import com.wu.library.repositories.admin.UserRepository;
import com.wu.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.getAllUser();
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public int update(User user, String id) {
        return 0;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int insert(User user) {
        return this.userRepository.insertUser(user);
    }
}
