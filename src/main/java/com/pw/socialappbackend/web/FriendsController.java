package com.pw.socialappbackend.web;

import com.pw.socialappbackend.service.TweetService;
import com.pw.socialappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private TweetService tweetService;
    private UserService userService;

    @Autowired
    public FriendsController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @GET
    @RequestMapping("/fetch-friends/{user}")
    public Response fetchFriends(@PathVariable("user") String user) {

        return Response.status(Response.Status.OK)
                .entity(userService.fetchUsersFriends(user))
                .build();
    }

    @GET
    @RequestMapping("/add-friend/{follower}/{followed}")
    public Response addFriend(@PathVariable("follower") String follower,
                              @PathVariable("followed") String followed) {

        userService.addFriend(follower, followed);

        return Response.status(Response.Status.OK)
                .entity(follower)
                .build();
    }

    @GET
    @RequestMapping("/remove-friend/{follower}/{followed}")
    public Response removeFriend(@PathVariable("follower") String follower,
                              @PathVariable("followed") String followed) {

        userService.removeFriend(follower, followed);

        return Response.status(Response.Status.OK)
                .entity(follower)
                .build();
    }
}
