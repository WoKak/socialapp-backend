package com.pw.socialappbackend.service;

import com.pw.socialappbackend.model.User;

public interface AuthenticationService {

    boolean isTokenInRequestIsValidForUser(String token, String user);
    boolean authenticateUser(User user);
    User prepareResponse(String username);
    String generateToken();
    void invalidateToken(String token);
    void registerUser(User userToRegister);
    boolean checkIfUserExists(User user);
}
