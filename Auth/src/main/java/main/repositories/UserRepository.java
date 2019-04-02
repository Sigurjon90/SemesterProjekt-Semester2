package main.repositories;

import main.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {

    private Connection connection;

    public UserRepository(
            @Value("${database.connection}") String connection,
            @Value("${database.username}") String username,
            @Value("${database.password}") String password) {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(connection, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement userInsert = this.connection.prepareStatement(
                    "INSERT INTO Users Values (?, ?)");
            userInsert.setString(1, user.getId());
            userInsert.setString(2, user.getName());
            userInsert.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try {
            PreparedStatement userLookup = this.connection.prepareStatement(
                    "SELECT * FROM Users WHERE id = ?");
            userLookup.setString(1, id);
            ResultSet userResult = userLookup.executeQuery();
            while(userResult.next()) {
                return Optional.ofNullable(new User(userResult.getString("id"), userResult.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement usersLookup = this.connection.prepareStatement(
                    "SELECT * FROM Users");
            ResultSet usersResult = usersLookup.executeQuery();
            while(usersResult.next()) {
                users.add(new User(usersResult.getString("id"), usersResult.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
