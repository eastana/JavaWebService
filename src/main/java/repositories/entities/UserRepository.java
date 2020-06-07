package repositories.entities;

import domain.UserLoginData;
import domain.models.User;
import repositories.db.PostgresRepository;
import repositories.interfaces.IUserRepository;

import javax.ws.rs.BadRequestException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserRepository implements IUserRepository {

    private Connection postgresConn = PostgresRepository.getConnection();

    @Override
    public void add(User entity) {
        try {
            String sql = "INSERT INTO users(username, password, role) " +
                    "VALUES(?, ?, ?)";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getRole());
            stmt.execute();
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET ";
        if (entity.getUsername() != null) sql += "username=?,";
        if (entity.getPassword() != null) sql += "password=?,";
        if (entity.getRole() != null) sql += "role=?,";

        sql = sql.substring(0, sql.length() - 1);
        sql += " WHERE username=?";

        try {
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            int i = 1;
            if (entity.getUsername() != null) stmt.setString(i++, entity.getUsername());
            if (entity.getPassword() != null) stmt.setString(i++, entity.getPassword());
            if (entity.getRole() != null) stmt.setString(i++, entity.getRole());
            stmt.setString(i++, entity.getUsername());

            stmt.execute();
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }

    @Override
    public void remove(User entity) {

    }

    @Override
    public List<User> query(String sql) {
        try {
            Statement stmt = postgresConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            LinkedList<User> users = new LinkedList<>();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getSQLState());
        }
    }

    @Override
    public User queryOne(String sql) {
        try {
            Statement stmt = postgresConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
            }
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
        return null;
    }

    public User getUserByID(long id) {
        String sql = "SELECT * FROM users WHERE id = " + id + " LIMIT 1";
        return queryOne(sql);
    }

    public User findUserByLogin(UserLoginData data) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setString(1, data.getUsername());
            stmt.setString(2, data.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
        return null;
    }

    public User getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? LIMIT 1";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
        return null;
    }

}
