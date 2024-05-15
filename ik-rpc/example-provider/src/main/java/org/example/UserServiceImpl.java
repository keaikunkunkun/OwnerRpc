package org.example;

import org.example.domain.User;
import org.example.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserName(User user) {
        System.out.println(user.getName());
        return user;
    }
}
