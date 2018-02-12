package com.pw.socialappbackend.service;

import com.pw.socialappbackend.model.User;

public interface AuthenticationService {

    boolean isTokenInRequestIsValidForUser(String token, String user);
    boolean authenticateUser(String username, String password);
    User prepareResponse();
    String generateToken();
    void invalidateToken(String token);
    void registerUser(User userToRegister);
}
