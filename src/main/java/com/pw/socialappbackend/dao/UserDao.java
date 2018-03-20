package com.pw.socialappbackend.dao;

import com.pw.socialappbackend.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-02-13.
 */
public class UserDao {

    private final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDao(DataSource dataSource,PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateUserPassword(User user) {

        String hashedPasswordFromDb = "";

        try {

            Connection connection = dataSource.getConnection();
            String getUserHashedPassword = "SELECT password FROM users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(getUserHashedPassword);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hashedPasswordFromDb = resultSet.getString("password");
            } else {
                return false;
            }
        } catch (SQLException ex) {
            logger.info("SQLException during validating user password");
            logger.info(ex.getMessage());
        }

        String hashedUserPassword = passwordEncoder.encode(user.getPassword());

        return hashedUserPassword.equals(hashedPasswordFromDb);
    }

    public void insertUserIntoDb(User user) {

        try {
            Connection connection = dataSource.getConnection();

            String insertUserToDb = "INSERT INTO users(username, password) VALUES (?, ?)";

            String hash = passwordEncoder.encode(user.getPassword());
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserToDb);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hash);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.info("SQLExecption during inserting user into DB");
            logger.info(e.getMessage());
        }
    }

    public boolean checkIfUserNameInDb(String userName) {

        try {
            Connection connection = dataSource.getConnection();
            String query = "SELECT * FROM users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            logger.info("SQLExecption during validation if user exists in DB");
            logger.info(e.getMessage());
        }

        return false;
    }
    public void addTokenToDB(String token,String username){
        try {
            Connection connection=dataSource.getConnection();

            String insertTokenToDb = "UPDATE users SET token=? WHERE username=?";

            PreparedStatement preparedStatement=connection.prepareStatement(insertTokenToDb);
            preparedStatement.setString(1,token);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();
            logger.info(preparedStatement.toString());

        } catch (SQLException e) {
            logger.info("SQLExecption during inserting token into DB");
            logger.info(e.getMessage());
        }
    }
    public Boolean getTokenForUser(String token){
        try {
            Connection connection=dataSource.getConnection();
            String getTokenForUser="SELECT * FROM users WHERE token=?";

            PreparedStatement preparedStatement=connection.prepareStatement(getTokenForUser);

            preparedStatement.setString(1,token);
            logger.info("Query: " + preparedStatement.toString());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                logger.info("Getting token for user");
                String username = resultSet.getString("username");
                logger.info("username: " + username);
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            logger.info("SQLExecption during geting token from DB");
            logger.info(e.getMessage());
        }
        return false;
    }

    public void invalidateTokenInDb(String user) {
        try {
            Connection connection = dataSource.getConnection();

            String invalidateToken = "UPDATE users SET token=NULL WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(invalidateToken);
            preparedStatement.setString(1, user);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.info("SQLExecption during invalidating token in DB");
            logger.info(e.getMessage());
        }
    }

    public Integer fetchSettingsFlag(String user) {

        int flag = 0;

        try {
            Connection connection = dataSource.getConnection();
            String query = "SELECT settings FROM users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                flag = resultSet.getInt(1);
            } else {
                throw new IllegalStateException("There's no user like: " + user + "!");
            }

        } catch (SQLException | IllegalStateException ex) {
            logger.info("SQLExecption during checking users settings in DB");
            logger.info(ex.getMessage());
        }

        return flag;
    }

    public void changeUserSettings(String user) {

        int flag;

        try {
            Connection connection = dataSource.getConnection();
            String query = "SELECT settings FROM users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                flag = resultSet.getInt(1);
            } else {
                throw new IllegalStateException("There's no user like: " + user + "!");
            }

            String changeQuery = "UPDATE users SET settings=? WHERE username=?";
            PreparedStatement preparedUpdateStatement = connection.prepareStatement(changeQuery);
            int toUpdate = flag > 0 ? 0 : 1;
            preparedUpdateStatement.setInt(1, toUpdate);
            preparedUpdateStatement.setString(2, user);
            preparedUpdateStatement.executeUpdate();

        } catch (SQLException | IllegalStateException ex) {
            logger.info("SQLExecption during checking users settings in DB");
            logger.info(ex.getMessage());
        }

    }

    public List<String> fetchUsersFriends(String user) {

        int userId = findUserId(user);
        ArrayList<String> listOfFriends = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            String getUserId =
                    "SELECT u.username FROM users u " +
                    "WHERE u.id IN (SELECT followed FROM relationships WHERE follower=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(getUserId);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listOfFriends.add(resultSet.getString(1));
            }

        } catch (SQLException ex) {
            logger.info("SQLExecption during fetching list of friends");
            logger.info(ex.getMessage());
        }

        return listOfFriends;
    }

    public void addFriend(String follower, String followed) {

        int followerId = findUserId(follower);
        int followedId = findUserId(followed);

        try {
            Connection connection = dataSource.getConnection();
            String query = "INSERT INTO relationships VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, followerId);
            preparedStatement.setInt(2, followedId);
            preparedStatement.executeUpdate();

        } catch (SQLException | IllegalStateException ex) {
            logger.info("SQLExecption during inserting relationship into DB");
            logger.info(ex.getMessage());
        }
    }

    private int findUserId(String username) {

        int userId = 0;

        try {
            Connection connection = dataSource.getConnection();
            String getUserId = "SELECT id FROM users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(getUserId);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                userId = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            logger.info("SQLExecption during checking user's id");
            logger.info(ex.getMessage());
        }

        return userId;
    }

    public void removeFriend(String follower, String followed) {

        int followerId = findUserId(follower);
        int followedId = findUserId(followed);

        try {
            Connection connection = dataSource.getConnection();
            String query = "DELETE FROM relationships WHERE follower = ? AND followed = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, followerId);
            preparedStatement.setInt(2, followedId);
            preparedStatement.executeUpdate();

        } catch (SQLException | IllegalStateException ex) {
            logger.info("SQLExecption during inserting relationship into DB");
            logger.info(ex.getMessage());
        }
    }
}
