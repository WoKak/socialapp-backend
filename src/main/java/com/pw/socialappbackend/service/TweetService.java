package com.pw.socialappbackend.service;

import com.pw.socialappbackend.model.Tweet;

import java.util.Arrays;

public interface TweetService {

    default Tweet[] fetchTweets() {

        Tweet t3 = new Tweet("Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.", "Bob");
        Tweet t2 = new Tweet("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.", "Alice");
        Tweet t1 = new Tweet("The quick, brown fox jumps over a lazy dog. DJs flock by when MTV ax quiz prog. Junk MTV quiz graced by fox whelps. Bawds jog, flick quartz, vex nymphs. Waltz, bad nymph, for quick jigs vex! Fox nymphs grab quick-jived waltz. Brick quiz whangs jumpy.", "Ben");

        Tweet[] response = new Tweet[3];
        response[0] = t1;
        response[1] = t2;
        response[2] = t3;

        return response;
    }
}
