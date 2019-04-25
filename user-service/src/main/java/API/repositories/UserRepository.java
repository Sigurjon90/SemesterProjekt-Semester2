/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import API.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jacobwowkjorgensen
 */
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
    public List<User> getAllUsers(UUID careCenterId) {
        List<User> users = new ArrayList();
        try (PreparedStatement getUsers = this.connection.prepareStatement("SELECT *  FROM users WHERE active=true AND care_center_id=?")) {
            getUsers.setObject(1, careCenterId, Types.OTHER);
            ResultSet usersResult = getUsers.executeQuery();

            while (usersResult.next()) {
                users.add(new User((UUID) usersResult.getObject("id"), usersResult.getString("username"), usersResult.getString("email"), usersResult.getString("address"), usersResult.getString("role"), usersResult.getString("cpr")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean usernameExist(String username) {
        User user = findUserByUsername(username);
        return user != null;
    }

    @Override
    public User CreateUser(User user, UUID careCenterId) {
        if (usernameExist(user.getUsername())) {
            return null;
        }

        try {
            connection.setAutoCommit(false);
            PreparedStatement createUser = this.connection.prepareStatement("INSERT INTO users VALUES (?, ?, ? , ? , ? , ? , ?, ?, ?) RETURNING id, username, email, active, address, role, cpr");
            createUser.setObject(1, UUID.randomUUID(), Types.OTHER);
            createUser.setString(2, user.getUsername());
            createUser.setString(3, user.getPassword());
            createUser.setString(4, user.getRole());
            createUser.setBoolean(5, true);
            createUser.setString(6, user.getEmail());
            createUser.setString(7, user.getCpr());
            createUser.setString(8, user.getAddress());
            createUser.setObject(9, careCenterId, Types.OTHER);

            ResultSet userResult = createUser.executeQuery();
            User userReturn = null;
            while (userResult.next()) {
                userReturn = new User((UUID) userResult.getObject("id"), userResult.getString("username"), userResult.getString("email"), userResult.getBoolean("active"), userResult.getString("address"), userResult.getString("role"), userResult.getString("cpr"));
            }
            if (user.getCitizensIDList() != null) {
                for (UUID citizenId : user.getCitizensIDList()) {
                    PreparedStatement insertIntoMyCitizens = this.connection.prepareStatement("INSERT INTO my_citizens VALUES (?, ?)");
                    insertIntoMyCitizens.setObject(1, userReturn.getId(), Types.OTHER);
                    insertIntoMyCitizens.setObject(2, citizenId, Types.OTHER);
                    insertIntoMyCitizens.execute();
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return userReturn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserById(UUID id) {
        try (PreparedStatement findUser = this.connection.prepareStatement("SELECT * FROM users WHERE id = ? AND active=true")) {
            findUser.setObject(1, id, Types.OTHER);
            ResultSet findUserResult = findUser.executeQuery();
            while (findUserResult.next()) {
                return new User((UUID) findUserResult.getObject("id"), findUserResult.getString("username"), findUserResult.getBoolean("active"), findUserResult.getString("role"), findUserResult.getString("email"), findUserResult.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        try (PreparedStatement findUser = this.connection.prepareStatement("SELECT *, (SELECT array(SELECT citizen_id FROM my_citizens WHERE my_citizens.user_id = users.id)) AS assignedCitizens FROM users WHERE username = ? AND active=true")) {
            findUser.setString(1, username);
            ResultSet findUsernameResult = findUser.executeQuery();
            while (findUsernameResult.next()) {
                Array arrayOfCitizens = findUsernameResult.getArray("assignedCitizens");
                List<UUID> myCitizens = Arrays.asList((UUID[])arrayOfCitizens.getArray());
                return new User((UUID) findUsernameResult.getObject("id"),
                        findUsernameResult.getString("username"),
                        findUsernameResult.getString("password"),
                        findUsernameResult.getString("role"),
                        findUsernameResult.getBoolean("active"),
                        findUsernameResult.getString("email"),
                        findUsernameResult.getString("cpr"),
                        findUsernameResult.getString("address"),
                        myCitizens,
                        (UUID) findUsernameResult.getObject("care_center_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUserById(UUID id) {
        try (PreparedStatement deleteUser = this.connection.prepareStatement("UPDATE users SET active = false WHERE id = ? ")) {
            deleteUser.setObject(1, id, Types.OTHER);
            int affectedRows = deleteUser.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User updateUser(User user) {
        try (PreparedStatement update = connection.prepareStatement("UPDATE users SET username = ?, password = ? , email = ? , address = ?  WHERE id = ?  RETURNING id , username, password, email, active, address, role, cpr, care_center_id")) {
            update.setString(1, user.getUsername());
            update.setString(2, user.getPassword());
            update.setString(3, user.getEmail());
            update.setString(4, user.getAddress());
            update.setObject(5, user.getId(), Types.OTHER);
            ResultSet updateResult = update.executeQuery();
            while (updateResult.next()) {
                return new User((UUID) updateResult.getObject("id"), updateResult.getString("username"), updateResult.getString("password"), updateResult.getString("email"), updateResult.getBoolean("active"), updateResult.getString("address"), updateResult.getString("role"), updateResult.getString("cpr"), null, (UUID)updateResult.getObject("care_center_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UUID> findCitizensById(UUID id) {
        List<UUID> citizens = new ArrayList<>();
        try (PreparedStatement findCitizen = this.connection.prepareStatement("SELECT * FROM my_citizens WHERE user_id = ?")) {
            findCitizen.setObject(1, id, Types.OTHER);
            ResultSet findCitizenResult = findCitizen.executeQuery();
            while (findCitizenResult.next()) {
                citizens.add((UUID) findCitizenResult.getObject("citizens_id"));
            }
            return citizens;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
