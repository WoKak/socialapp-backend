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

        boolean isUserAuthenticated = authenticationService.authenticateUser(userToAuthenticate);


        if(!isUserAuthenticated) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(authenticationService.prepareResponse(userToAuthenticate.getUsername()))
                .build();
    }

    @POST
    @RequestMapping("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(@RequestBody User userToLogout) {

        authenticationService.invalidateToken(userToLogout.getUsername());

        return Response.status(Response.Status.OK)
                .entity(new User(userToLogout.getUsername()))
                .build();
    }

    @POST
    @RequestMapping("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@RequestBody User userToRegister) {

        if (authenticationService.checkIfUserExists(userToRegister)){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("User exist")
                    .build();
        }

        authenticationService.registerUser(userToRegister);

        return Response.status(Response.Status.OK)
                .entity(userToRegister.getUsername())
                .build();
    }
}
