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
        this.passwordEncoder=passwordEncoder;
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
            logger.info(preparedStatement.toString());

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
    public String getTokenForUser(String user){
        String token=null;
        try {
            Connection connection=dataSource.getConnection();
            String getTokenForUser="SELECT token FROM users WHERE username=?";

            PreparedStatement preparedStatement=connection.prepareStatement(getTokenForUser);
            preparedStatement.setString(1,user);
            ResultSet resultSet=preparedStatement.executeQuery();

            token=resultSet.getString("token");

        } catch (SQLException e) {
            logger.info("SQLExecption during geting token from DB");
            logger.info(e.getMessage());
        }
        return token;
    }

    public void invalidateTokenInDb(String user){
        try {
            Connection connection=dataSource.getConnection();

            String invalidateToken="UPDATE users SET token= NULL WHERE username=?";
            PreparedStatement preparedStatement=connection.prepareStatement(invalidateToken);
            preparedStatement.setString(1,user);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.info("SQLExecption during invalidating token in DB");
            logger.info(e.getMessage());
        }

    }
}
