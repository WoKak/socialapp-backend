package com.pw.socialappbackend.service;

import com.pw.socialappbackend.model.Tweet;

import java.util.List;

public interface TweetService {

    List<Tweet> fetchTweets(String username);
    List<Tweet> fetchUsersTweets(String username);
    long addTweet(Tweet tweetToAdd);
    void flagTweet(int offensiveTweetId);
}
