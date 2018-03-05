package com.pw.socialappbackend.integration;

import java.util.ArrayList;

public class NeuralNetworkDto {

    private ArrayList<Integer> posts;

    public NeuralNetworkDto() {
        this.posts = new ArrayList<>();
    }

    public void add(int toAdd) {
        this.posts.add(toAdd);
    }

    public ArrayList<Integer> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Integer> posts) {
        this.posts = posts;
    }
}


