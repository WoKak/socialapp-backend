package com.pw.socialappbackend.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.pw.socialappbackend.model.User;
import com.pw.socialappbackend.service.AuthenticationService;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Random;

//TODO: write implementation
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public boolean isTokenInRequestIsValidForUser(String token, String user) {
        return false;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        if(!"user".equals(username) || !"pass".equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public User prepareResponse() {
        return null;
    }

    @Override
    public String generateToken() {
        Random random = new Random();
        HashFunction hf = Hashing.sha512();
        HashCode hc = hf.newHasher().putString(String.valueOf(random.nextLong()), Charsets.UTF_8).hash();
        String token = hc.toString();
        return token;
    }

    @Override
    public void invalidateToken(String token) {

    }

    @Override
    public void registerUser(User userToRegister) {

    }
}
