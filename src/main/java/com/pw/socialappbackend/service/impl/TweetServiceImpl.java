package com.pw.socialappbackend.service.impl;

import com.pw.socialappbackend.dao.TweetDao;
import com.pw.socialappbackend.dao.UserDao;
import com.pw.socialappbackend.model.Tweet;
import com.pw.socialappbackend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TweetServiceImpl implements TweetService{

    private TweetDao tweetDao;

    @Autowired
    public TweetServiceImpl(TweetDao tweetDao) {
        this.tweetDao = tweetDao;
    }

    @Override
    public List<Tweet> fetchTweets(String username) {
        return tweetDao.fetchTweets(username);
    }

    @Override
    public List<Tweet> fetchUsersTweets(String username) {
        return tweetDao.fetchUsersTweets(username);
    }

    @Override
    public void addTweet(Tweet tweetToAdd) {
        int index = tweetDao.add(tweetToAdd);
        //TODO: call AI endpoint in order to validate tweet
    }
}
