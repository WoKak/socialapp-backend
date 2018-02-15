package com.pw.socialappbackend.web;

import com.pw.socialappbackend.model.Tweet;
import com.pw.socialappbackend.model.User;
import com.pw.socialappbackend.service.TweetService;
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

    @Autowired
    public ProfileController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GET
    @RequestMapping("/fetch-users-tweets/{user}")
    public Response fetchUsersTweets(@PathVariable("user") String user) {

        return Response.status(200)
                .entity(tweetService.fetchUsersTweets(user))
                .build();
    }

    @POST
    @RequestMapping("/tweet")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(@RequestBody Tweet tweet) {

        tweetService.addTweet(tweet);

        return Response.status(200)
                .entity(tweet)
                .build();
    }
}
