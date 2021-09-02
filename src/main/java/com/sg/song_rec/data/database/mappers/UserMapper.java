package com.sg.song_rec.data.database.mappers;

import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.OAuth2Token;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        OAuth2Token token = new OAuth2Token();
        token.setAccessToken(resultSet.getString("access_token"));
        token.setRefreshToken(resultSet.getString("refresh_token"));
        token.setExpirationDate(resultSet.getTimestamp("expiration_date").toLocalDateTime());
        user.setToken(token);
        return user;
    }
}
