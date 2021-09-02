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

@Repository
public class UserDbDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    @Override
    public void addUser(User user) {
        final String sql = "INSERT INTO user(id, access_token, refresh_token, expiration_date) VALUES(?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getId(),
                user.getToken().getAccessToken(),
                user.getToken().getRefreshToken(),
                Timestamp.valueOf(user.getToken().getExpirationDate()));
    }

    @Override
    public void removeUserById(String id) {
        final String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateUser(User user) {
        final String sql = "UPDATE user SET access_token = ?, refresh_token = ?, expiration_date = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getToken().getAccessToken(),
                user.getToken().getRefreshToken(),
                Timestamp.valueOf(user.getToken().getExpirationDate()),
                user.getId());
    }

    @Override
    public List<User> getAllUsers() {
        final String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public void removeExpiredUsers() {
        final String sql = "DELETE FROM user WHERE user.expiration_date < ?";
        jdbcTemplate.update(sql, LocalDateTime.now());
    }
}
