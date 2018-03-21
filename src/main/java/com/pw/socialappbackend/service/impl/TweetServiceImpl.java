package com.pw.socialappbackend.service.impl;

import com.pw.socialappbackend.dao.TweetDao;
import com.pw.socialappbackend.integration.NeuralNetworkDto;
import com.pw.socialappbackend.model.Tweet;
import com.pw.socialappbackend.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TweetServiceImpl implements TweetService {

    private final Logger logger = LoggerFactory.getLogger(TweetServiceImpl.class);

    private TweetDao tweetDao;

    @Value("${neural-network.url}")
    private String url;

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
        NeuralNetworkDto dto = new NeuralNetworkDto();
        dto.add(index);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic YRWtaW46c2VjcmV0");
        headers.add("Content-Type", "application/json");
        HttpEntity<NeuralNetworkDto> entity = new HttpEntity<>(dto, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if ("OK".equals(response.getBody())) {
            logger.info("New tweet was sent for validation");
        } else {
            logger.info("Problem during sending tweet for validation");
        }
    }

    @Override
    public void flagTweet(int offensiveTweetId) {
        tweetDao.flagTweet(offensiveTweetId);
    }


}
