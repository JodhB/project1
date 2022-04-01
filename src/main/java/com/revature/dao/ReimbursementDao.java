package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDao {

    public List<Reimbursement> getReimbursements() throws SQLException {
        try(Connection con = ConnectionUtility.getConnection())  {
            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT reimbursements.id as reimbursement_id, amount, submitted, resolved, description, receipt, status, type, author_user.id as author_id, author_user.username as author_username, author_user.password as author_password, author_user.first_name as author_first_name, author_user.last_name as author_last_name, author_user.email as author_email, resolver_user.id as resolver_id, resolver_user.username as resolver_username, resolver_user.password as resolver_password, resolver_user.first_name as resolver_first_name, resolver_user.last_name as resolver_last_name, resolver_user.email as resolver_email " +
                    "FROM reimbursements " +
                    "LEFT JOIN users author_user " +
                    "ON author_user.id = reimbursements.author " +
                    "LEFT JOIN users resolver_user " +
                    "ON resolver_user.id = reimbursements.resolver "  +
                    "LEFT JOIN reimbursement_status " +
                    "ON reimbursement_status.id = reimbursements.status_id " +
                    "LEFT JOIN reimbursement_type " +
                    "ON reimbursement_type.id = reimbursements.type_id";

            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int reimbursementId = rs.getInt("reimbursement_id");
                int amount = rs.getInt("amount");
                String submitted = rs.getTimestamp("submitted").toString();
                String resolved = rs.getTimestamp("resolved") == null ? null : rs.getTimestamp("resolved").toString();
                String description = rs.getString("description");
                String receipt = rs.getString("receipt");
                String status = rs.getString("status");
                String type = rs.getString("type");

                int authorId = rs.getInt("author_id");
                String authorUsername = rs.getString("author_username");
                String authorPassword = rs.getString("author_password");
                String authorFirstName = rs.getString("author_first_name");
                String authorLastName = rs.getString("author_last_name");
                String authorEmail = rs.getString("author_email");

                User author = new User(authorId, authorUsername, authorPassword, authorFirstName, authorLastName, authorEmail, "Employee");

                int resolverId = rs.getInt("resolver_id");
                String resolverUsername = rs.getString("resolver_username");
                String resolverPassword = rs.getString("resolver_password");
                String resolverFirstName = rs.getString("resolver_first_name");
                String resolverLastName = rs.getString("resolver_last_name");
                String resolverEmail = rs.getString("resolver_email");

                User resolver = new User(resolverId, resolverUsername, resolverPassword, resolverFirstName, resolverLastName, resolverEmail, "Manager");

                reimbursements.add(new Reimbursement(reimbursementId, amount, submitted, resolved, description, receipt, author, resolver, status, type));
            }

            return reimbursements;
        }
    }

    public List<Reimbursement> getReimbursements(int userId) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection())  {
            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT reimbursements.id as reimbursement_id, amount, submitted, resolved, description, receipt, status, type, author_user.id as author_id, author_user.username as author_username, author_user.password as author_password, author_user.first_name as author_first_name, author_user.last_name as author_last_name, author_user.email as author_email, resolver_user.id as resolver_id, resolver_user.username as resolver_username, resolver_user.password as resolver_password, resolver_user.first_name as resolver_first_name, resolver_user.last_name as resolver_last_name, resolver_user.email as resolver_email " +
                    "FROM reimbursements " +
                    "LEFT JOIN users author_user " +
                    "ON author_user.id = reimbursements.author " +
                    "LEFT JOIN users resolver_user " +
                    "ON resolver_user.id = reimbursements.resolver "  +
                    "LEFT JOIN reimbursement_status " +
                    "ON reimbursement_status.id = reimbursements.status_id " +
                    "LEFT JOIN reimbursement_type " +
                    "ON reimbursement_type.id = reimbursements.type_id " +
                    "WHERE reimbursements.author = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int reimbursementId = rs.getInt("reimbursement_id");
                int amount = rs.getInt("amount");
                String submitted = rs.getTimestamp("submitted").toString();
                String resolved = rs.getTimestamp("resolved") == null ? null : rs.getTimestamp("resolved").toString();
                String description = rs.getString("description");
                String receipt = rs.getString("receipt");
                String status = rs.getString("status");
                String type = rs.getString("type");

                int authorId = rs.getInt("author_id");
                String authorUsername = rs.getString("author_username");
                String authorPassword = rs.getString("author_password");
                String authorFirstName = rs.getString("author_first_name");
                String authorLastName = rs.getString("author_last_name");
                String authorEmail = rs.getString("author_email");

                User author = new User(authorId, authorUsername, authorPassword, authorFirstName, authorLastName, authorEmail, "Employee");

                int resolverId = rs.getInt("resolver_id");
                String resolverUsername = rs.getString("resolver_username");
                String resolverPassword = rs.getString("resolver_password");
                String resolverFirstName = rs.getString("resolver_first_name");
                String resolverLastName = rs.getString("resolver_last_name");
                String resolverEmail = rs.getString("resolver_email");

                User resolver = new User(resolverId, resolverUsername, resolverPassword, resolverFirstName, resolverLastName, resolverEmail, "Manager");

                reimbursements.add(new Reimbursement(reimbursementId, amount, submitted, resolved, description, receipt, author, resolver, status, type));
            }

            return reimbursements;
        }
    }

    public boolean addReimbursement(int userId, AddReimbursementDTO dto, String url) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "INSERT INTO reimbursements (amount, submitted, description, receipt, author, status_id, type_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, dto.getAmount());
            pstmt.setTimestamp(2, Timestamp.valueOf(dto.getSubmitted()));
            pstmt.setString(3, dto.getDescription());
            pstmt.setString(4, url);
            pstmt.setInt(5, userId);
            pstmt.setInt(6, 1); // Pending status
            pstmt.setInt(7, dto.getType());

            if(pstmt.executeUpdate() == 1) return true;
            return false;
        }
    }

    public boolean handleReimbursement(int id, int status, int resolver, String resolved) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "UPDATE reimbursements " +
                    "SET resolved = ?, " +
                    "resolver = ?, " +
                    "status_id = ? " +
                    "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setTimestamp(1, Timestamp.valueOf(resolved));
            pstmt.setInt(2, resolver);
            pstmt.setInt(3, status);
            pstmt.setInt(4, id);

            if(pstmt.executeUpdate() == 1) return true;
            return false;
        }
    }
}
