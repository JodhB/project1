package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;

import javax.security.auth.login.FailedLoginException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public UserService(UserDao mockDao) {
        this.userDao = mockDao;
    }

    public User login(String username, String password) throws SQLException, FailedLoginException, NoSuchAlgorithmException {
        String hashedPassword = generateHash(password, "SHA-256");
        User user = this.userDao.getUserByUsernameAndPassword(username, hashedPassword);

        if (user == null) {
            throw new FailedLoginException("Invalid username and/or password was provided");
        }

        return user;
    }

    private static String generateHash(String data, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();
        byte[] hash = digest.digest(data.getBytes());
        return bytesToStringHex(hash);
    }

    private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for(int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}