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
            logger.info("SQLException during validateUserPassword");
            logger.info(ex.getMessage());
        }

        String hashedUserPassword = passwordEncoder.encode(user.getPassword());

        return hashedUserPassword.equals(hashedPasswordFromDb);
    }

    public void insertUserIntoDb(User user) {
        //TODO Add user to Db
        try {
            Connection connection = dataSource.getConnection();

            String insertUserToDb = "INSERT INTO users(username, password) VALUES (?, ?)";
            String insertUserRoleToDb = "INSERT INTO users_roles(username, role) VALUES (?, 'ROLE_USER')";

            //For User
            String hash = passwordEncoder.encode(user.getPassword());
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserToDb);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hash);
            preparedStatement.executeUpdate();
            logger.debug(preparedStatement.toString());
            //For User role
            PreparedStatement preparedStatementForRole = connection.prepareStatement(insertUserRoleToDb);
            preparedStatement.setString(1, user.getUsername());
            logger.debug(preparedStatement.toString());
            preparedStatementForRole.executeUpdate();


        } catch (SQLException e) {
            logger.info("SQLExecption during insert user into Db");
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

            if (resultSet.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            logger.info("SQLExecption during validation if user exists in Db");
            logger.info(e.getMessage());
        }


        return false;
    }

}
