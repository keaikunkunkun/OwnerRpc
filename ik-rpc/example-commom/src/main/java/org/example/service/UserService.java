package org.example.service;

import org.example.domain.User;

public interface UserService {
    User getUserName(User user);

    default short getNumber() {
        return  1;
    }
}
