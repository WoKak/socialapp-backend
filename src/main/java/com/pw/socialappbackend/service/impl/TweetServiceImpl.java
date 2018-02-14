package com.pw.socialappbackend.service.impl;

import com.pw.socialappbackend.model.Tweet;
import com.pw.socialappbackend.service.TweetService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TweetServiceImpl implements TweetService{

    private ArrayList<Tweet> tweets;

    public TweetServiceImpl() {
        this.tweets = new ArrayList<>();
        Tweet t3 = new Tweet(
                "Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.",
                "bob"
        );
        Tweet t2 = new Tweet(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.",
                "alice"
        );
        Tweet t1 = new Tweet(
                "The quick, brown fox jumps over a lazy dog. DJs flock by when MTV ax quiz prog. Junk MTV quiz graced by fox whelps. Bawds jog, flick quartz, vex nymphs. Waltz, bad nymph, for quick jigs vex! Fox nymphs grab quick-jived waltz. Brick quiz whangs jumpy.",
                "user"
        );
        tweets.add(t1);
        tweets.add(t2);
        tweets.add(t3);
    }

    @Override
    public List<Tweet> fetchTweets() {
        return tweets;
    }

    @Override
    public List<Tweet> fetchUsersTweets(String username) {
        return tweets.stream().filter(tweet -> tweet.getOwner().equals(username)).collect(Collectors.toList());
    }

    @Override
    public void addTweet(Tweet tweetToAdd) {
        tweets.add(tweetToAdd);
    }
}
