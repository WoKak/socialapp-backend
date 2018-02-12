package com.pw.socialappbackend.web;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.pw.socialappbackend.model.User;
import com.pw.socialappbackend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @POST
    @RequestMapping("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody User userToAuthenticate) {



        //Authorization
        //TODO: call service for authenticate
        if(authenticationService.authenticateUser(userToAuthenticate.getUsername(),userToAuthenticate.getPassword())) {
            return Response.status(401)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
                    .header("Access-Control-Allow-Headers", "X-Requested-With, Content-Type")
                    .build();
        }


        User authenticatedUser = new User();
        authenticatedUser.setUsername(userToAuthenticate.getUsername());
        authenticatedUser.setToken(authenticationService.generateToken());

        //end of code to refactor

        return Response.status(200)
                .entity(authenticatedUser)
                .build();
    }

    @POST
    @RequestMapping("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(@RequestBody User userToLogout) {

        //TODO: call service in order to invalid users token

        return Response.status(200)
                .entity(new User(userToLogout.getUsername()))
                .build();

    }

    @POST
    @RequestMapping("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@RequestBody User userToRegister) {

        //TODO: calling service in order to register user

        return Response.status(200)
                .build();
    }
}
