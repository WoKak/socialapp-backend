package com.pw.socialappbackend.service;

import java.util.List;

public interface UserService {

    Integer fetchUsersSettings(String user);
    void changeUsersSettings(String user);
    List<String> fetchUsersFriends(String user);
    void addFriend(String follower, String followed);
}
