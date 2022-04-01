package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public UserDao(){
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) { // Automatically closes the Connection object
            String sql = "SELECT users.id, users.first_name, users.last_name, users.email, user_roles.user_role " +
                    "FROM users " +
                    "INNER JOIN user_roles " +
                    "ON users.user_role_id = user_roles.id " +
                    "WHERE users.username = ? AND users.password = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String role = rs.getString("user_role");

                return new User(id, username, password, firstName, lastName, email, role);
            }

            return null;
        }
    }
}
