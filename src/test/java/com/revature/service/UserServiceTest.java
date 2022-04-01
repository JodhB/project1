package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserDao mockDao = mock(UserDao.class);
    private UserService userService = new UserService(mockDao);

    @Test
    public void test_login_positive() throws SQLException, FailedLoginException, NoSuchAlgorithmException {
        when(mockDao.getUserByUsernameAndPassword("john_doe", "A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3"))
                .thenReturn(new User(1, "john_doe", "123", "test", "test", "test", "test"));

        User actual = userService.login("john_doe", "123");
        User expected = new User(1, "john_doe", "123", "test", "test", "test", "test");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_login_invalid() {
        Assertions.assertThrows(FailedLoginException.class, () -> {
            userService.login("john_doe", "123");
        });
    }
}
