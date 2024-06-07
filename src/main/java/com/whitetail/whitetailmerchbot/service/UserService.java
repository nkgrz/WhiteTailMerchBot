package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.UserRepository;
import com.whitetail.whitetailmerchbot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(long chatId, String userName) {
        if (userRepository.findById(chatId).isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(userName);
            userRepository.save(user);
        }
        // TODO: Если username устарел, обновить
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(long chatId) {
        return userRepository.findById(chatId).orElse(null);
    }
}
