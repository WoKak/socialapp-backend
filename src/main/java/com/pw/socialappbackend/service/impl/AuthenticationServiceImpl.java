package com.pw.socialappbackend.service.impl;

import com.pw.socialappbackend.model.User;
import com.pw.socialappbackend.service.AuthenticationService;

//TODO: write implementation
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public boolean isTokenInRequestIsValidForUser(String token, String user) {
        return false;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return false;
    }

    @Override
    public User prepareResponse() {
        return null;
    }

    @Override
    public String generateToken() {
        return null;
    }

    @Override
    public void invalidateToken(String token) {

    }

    @Override
    public void registerUser(User userToRegister) {

    }
}
