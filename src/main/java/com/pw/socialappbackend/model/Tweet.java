package com.pw.socialappbackend.model;

public class Tweet {

    private int id;
    private String tweet;
    private String owner;

    public Tweet() {
    }

    public Tweet(String tweet, String owner, int id) {

        this.id = id;
        this.tweet = tweet;
        this.owner = owner;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
