package com.sg.song_rec.data.database;

import com.sg.song_rec.data.UserDao;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.OAuth2Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDbDaoTest {

    private UserDao dao;

    @Autowired
    public UserDbDaoTest(UserDao dao) {
        this.dao = dao;
    }

    @BeforeEach
    void setUp() {
        List<User> users = dao.getAllUsers();
        for(User u : users) {
            dao.removeUserById(u.getId());
        }
    }

    @Test
    void getAndAddUserTest() {
        User u = new User();
        OAuth2Token token = new OAuth2Token();
        token.setAccessToken("access");
        token.setRefreshToken("refresh");
        token.setExpirationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        u.setId("1");
        u.setToken(token);
        assertNull(dao.getUserById("1"));
        dao.addUser(u);
        User retrieved = dao.getUserById("1");
        assertNotNull(retrieved);
        assertEquals(retrieved, u);
    }

    @Test
    void removeUserById() {
        User u = new User();
        OAuth2Token token = new OAuth2Token();
        token.setAccessToken("access");
        token.setRefreshToken("refresh");
        token.setExpirationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        u.setId("1");
        u.setToken(token);
        dao.addUser(u);
        assertNotNull(dao.getUserById("1"));
        dao.removeUserById("1");
        assertNull(dao.getUserById("1"));
    }

    @Test
    void updateUser() {
        User u = new User();
        OAuth2Token token = new OAuth2Token();
        token.setAccessToken("access");
        token.setRefreshToken("refresh");
        token.setExpirationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        u.setId("1");
        u.setToken(token);
        dao.addUser(u);
        User retrieved = dao.getUserById("1");
        assertEquals(u, retrieved);
        u.getToken().setRefreshToken("refresh2");
        retrieved = dao.getUserById("1");
        assertNotEquals(u, retrieved);
        dao.updateUser(u);
        retrieved = dao.getUserById("1");
        assertEquals(u, retrieved);
    }

    @Test
    public void testRemoveExpiredUsers() {
        User notExpired = new User();
        notExpired.setId("1");
        OAuth2Token tNotExpired = new OAuth2Token();
        tNotExpired.setExpirationDate(LocalDateTime.now().plusDays(1));
        tNotExpired.setAccessToken("access");
        tNotExpired.setRefreshToken("refresh");
        notExpired.setToken(tNotExpired);
        User expired = new User();
        expired.setId("2");
        OAuth2Token tExpired = new OAuth2Token();
        tExpired.setExpirationDate(LocalDateTime.now().minusDays(1));
        tExpired.setAccessToken("access");
        tExpired.setRefreshToken("refresh");
        expired.setToken(tExpired);
        dao.addUser(notExpired);
        dao.addUser(expired);
        assertEquals(dao.getAllUsers().size(), 2);
        dao.removeExpiredUsers();
        assertEquals(dao.getAllUsers().size(), 1);
        User retrieved = dao.getUserById("1");
        assertEquals(retrieved, notExpired);
    }
}