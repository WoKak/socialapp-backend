package com.pw.socialappbackend.service.impl;

import com.pw.socialappbackend.dao.UserDao;
import com.pw.socialappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Integer fetchUsersSettings(String user) {
        return userDao.fetchSettingsFlag(user);
    }

    @Override
    public void changeUsersSettings(String user) {
        userDao.changeUserSettings(user);
    }

    @Override
    public List<String> fetchUsersFriends(String user) {
        return userDao.fetchUsersFriends(user);
    }

    @Override
    public void addFriend(String follower, String followed) {
        userDao.addFriend(follower, followed);
    }

    @Override
    public void removeFriend(String follower, String followed) {
        userDao.removeFriend(follower, followed);
    }
}
