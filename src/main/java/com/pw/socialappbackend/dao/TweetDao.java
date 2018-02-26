package com.pw.socialappbackend.dao;

import com.pw.socialappbackend.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TweetDao {

    private final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private DataSource dataSource;

    @Autowired
    public TweetDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Tweet> fetchTweets(String username) {

        ArrayList<Tweet> result = new ArrayList<>();
        int userId = findUserId(username);

        try {
            Connection connection = dataSource.getConnection();
            String getTweets =
                    "SELECT " +
                    "users.username, tweets.tweet " +
                    "FROM tweets INNER JOIN users ON (users.id = tweets.id_ownr) " +
                    "WHERE id_ownr IN (SELECT followed FROM relationships WHERE follower=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(getTweets);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                result.add(new Tweet(resultSet.getString(2), resultSet.getString(1)));
            }

        } catch (SQLException ex) {
            logger.info("SQLExecption during fetching tweets");
            logger.info(ex.getMessage());
        }

        return result;
    }

    public List<Tweet> fetchUsersTweets(String username) {

        //TODO: reimplement
        List<Tweet> tweets = fetchTweets(username);
        return tweets.stream().filter(tweet -> tweet.getOwner().equals(username)).collect(Collectors.toList());
    }

    public int add(Tweet tweetToAdd) {

        int ownerId = findUserId(tweetToAdd.getOwner());
        int lastTweetId = 0;

        try {
            Connection connection = dataSource.getConnection();
            String insertTweetQuery = "INSERT INTO tweets (tweet, id_ownr, flag) VALUES (?, ?, 0)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertTweetQuery);
            preparedStatement.setString(1, tweetToAdd.getTweet());
            preparedStatement.setInt(2, ownerId);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            logger.info("SQLExecption during adding tweet");
            logger.info(ex.getMessage());
        }

        try {
            Connection connection = dataSource.getConnection();
            String getLastTweetIdQuery = "SELECT id_twt FROM tweets ORDER BY id_twt DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(getLastTweetIdQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                lastTweetId = resultSet.getInt("id_twt");
            }

        } catch (SQLException ex) {
            logger.info("SQLExecption during checking last tweet's id");
            logger.info(ex.getMessage());
        }

        return lastTweetId;
    }

    private int findUserId(String owner) {

        int ownerId = 0;

        try {
            Connection connection = dataSource.getConnection();
            String getUserIdQuery = "SELECT id FROM users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(getUserIdQuery);
            preparedStatement.setString(1, owner);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                ownerId = resultSet.getInt("id");
            }

        } catch (SQLException ex) {
            logger.info("SQLExecption during checking owner's id");
            logger.info(ex.getMessage());
        }

        return ownerId;
    }
}
