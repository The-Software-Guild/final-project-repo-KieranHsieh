package com.sg.song_rec.data.database;

import com.sg.song_rec.data.UserDao;
import com.sg.song_rec.data.database.mappers.UserMapper;
import com.sg.song_rec.entities.application.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * An implementation of UserDao using
 * a SQL database to get users
 */
@Repository
public class UserDbDao implements UserDao {

    /**
     * THe JdbcTemplate used to make SQL queries
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new UserDbDao
     * @param jdbcTemplate The template used for SQL queries
     */
    @Autowired
    public UserDbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Gets a user given their ID
     * @param id The user's ID
     * @return THe retrieved user if it was present, or null if it was not
     */
    @Override
    public User getUserById(String id) {
        final String sql = "SELECT * FROM user WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        }
        catch(DataAccessException e) {
            return null;
        }
    }

    /**
     * Adds a user to the database
     * @param user The user to add
     */
    @Override
    public void addUser(User user) {
        final String sql = "INSERT INTO user(id, access_token, refresh_token, expiration_date) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getId(),
                user.getToken().getAccessToken(),
                user.getToken().getRefreshToken(),
                Timestamp.valueOf(user.getToken().getExpirationDate()));
    }

    /**
     * Removes a user from the database
     * @param id The ID of the user to remove
     */
    @Override
    public void removeUserById(String id) {
        final String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * Updates a user in the database
     * @param user The new value of the user.
     */
    @Override
    public void updateUser(User user) {
        final String sql = "UPDATE user SET access_token = ?, refresh_token = ?, expiration_date = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getToken().getAccessToken(),
                user.getToken().getRefreshToken(),
                Timestamp.valueOf(user.getToken().getExpirationDate()),
                user.getId());
    }

    /**
     * Gets all users in the database
     * @return All users
     */
    @Override
    public List<User> getAllUsers() {
        final String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    /**
     * Removes all expired users from the database
     */
    @Override
    public void removeExpiredUsers() {
        final String sql = "DELETE FROM user WHERE user.expiration_date < ?";
        jdbcTemplate.update(sql, LocalDateTime.now());
    }
}
