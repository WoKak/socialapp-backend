package com.pw.socialappbackend.web;

import com.pw.socialappbackend.model.Tweet;
import com.pw.socialappbackend.service.TweetService;
import com.pw.socialappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private TweetService tweetService;
    private UserService userService;

    @Autowired
    public ProfileController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @GET
    @RequestMapping("/fetch-users-tweets/{user}")
    public Response fetchUsersTweets(@PathVariable("user") String user) {

        return Response.status(Response.Status.OK)
                .entity(tweetService.fetchUsersTweets(user))
                .build();
    }

    @GET
    @RequestMapping("/fetch-users-settings/{user}")
    public Response fetchUsersSettings(@PathVariable("user") String user) {

        return Response.status(Response.Status.OK)
                .entity(userService.fetchUsersSettings(user))
                .build();
    }

    @GET
    @RequestMapping("/change-users-settings/{user}")
    public Response changeUsersSettings(@PathVariable("user") String user) {

        userService.changeUsersSettings(user);

        return Response.status(Response.Status.OK)
                .entity(user)
                .build();
    }

    @POST
    @RequestMapping("/tweet")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@RequestBody Tweet tweet) {

        return Response.status(Response.Status.OK)
                .entity(tweetService.addTweet(tweet))
                .build();
    }
}
