package com.pw.socialappbackend.web;

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

        if(!authenticationService.authenticateUser(userToAuthenticate)) {
            return Response.status(401)
                    .build();
        }

        return Response.status(200)
                .entity(authenticationService.prepareResponse(userToAuthenticate.getUsername()))
                .build();
    }

    @POST
    @RequestMapping("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(@RequestBody User userToLogout) {

        //TODO: call service in order to invalidate user's token

        return Response.status(200)
                .entity(new User(userToLogout.getUsername()))
                .build();
    }

    @POST
    @RequestMapping("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@RequestBody User userToRegister) {

        //TODO: calling service in order to register user
        if (authenticationService.checkIfUserExists(userToRegister)){
            return Response.status(500)
                    .entity("User exist")
                    .build();
        }
        return Response.status(200)
                .build();
    }
}
