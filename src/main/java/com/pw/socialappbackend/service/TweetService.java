package com.pw.socialappbackend.service;

import com.pw.socialappbackend.model.Tweet;

import java.util.List;

public interface TweetService {

    List<Tweet> fetchTweets();
    List<Tweet> fetchUsersTweets(String username);
    void addTweet(Tweet tweetToAdd);
    Integer fetchUsersSettings(String user);
    void changeUsersSettings(String user);
}
