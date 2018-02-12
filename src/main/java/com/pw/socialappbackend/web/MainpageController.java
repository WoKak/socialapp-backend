package com.pw.socialappbackend.web;

import com.pw.socialappbackend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/main")
public class MainpageController {

    @Autowired
    private TweetService tweetService;

    @GET
    @RequestMapping("/fetch-tweets")
    public Response fetchTweets() {

        return Response.status(200)
                .entity(tweetService.fetchTweets())
                .build();
    }
}
