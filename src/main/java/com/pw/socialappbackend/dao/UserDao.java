package com.pw.socialappbackend.dao;

import com.pw.socialappbackend.model.User;
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
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDao(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateUserPassword(User user) throws SQLException {
        String hashedPasswordFromDb;

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
        String hashUserPassword = passwordEncoder.encode(user.getPassword());

        if (hashUserPassword.equals(hashedPasswordFromDb)) {
            return true;
        }else{
            return false;
        }
    }
    public void insertUserIntoDb(User user){
        //TODO Add user to Db
    }
}
