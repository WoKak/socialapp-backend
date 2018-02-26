package com.pw.socialappbackend.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.pw.socialappbackend.dao.UserDao;
import com.pw.socialappbackend.model.User;
import com.pw.socialappbackend.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

//TODO: write implementation
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private UserDao userDao;

    @Autowired
    public AuthenticationServiceImpl (UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public boolean isTokenInRequestIsValidForUser(String token) {
        logger.info("checking token for user");

        return userDao.getTokenForUser(token);

    }

    @Override
    public boolean authenticateUser(User user) {

        return userDao.validateUserPassword(user);
    }

    @Override
    public User prepareResponse(String username) {

        User authenticatedUser = new User();
        String token=generateToken();
        authenticatedUser.setUsername(username);
        authenticatedUser.setToken(token);

        insertToken(token,username);
        return authenticatedUser;
    }

    @Override
    public String generateToken() {

        Random random = new Random();
        HashFunction hf = Hashing.sha512();
        HashCode hc = hf.newHasher().putString(String.valueOf(random.nextLong()), Charsets.UTF_8).hash();
        return hc.toString();
    }

    @Override
    public void invalidateToken(String user) {
        userDao.invalidateTokenInDb(user);
    }

    @Override
    public void registerUser(User userToRegister) {
        userDao.insertUserIntoDb(userToRegister);
    }

    @Override
    public boolean checkIfUserExists(User user) {
        return userDao.checkIfUserNameInDb(user.getUsername());
    }

    @Override
    public void insertToken(String token,String username) {
        userDao.addTokenToDB(token,username);
    }

}
