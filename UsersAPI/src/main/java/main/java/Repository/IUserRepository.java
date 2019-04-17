/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Repository;

import java.util.List;
import java.util.UUID;
import main.java.Entity.User;

/**
 *
 * @author madsfalken
 */
public interface IUserRepository {

    User CreateUser(User user);

    boolean deleteUserById(UUID id);

    List<UUID> findCitizensById(UUID id);

    User findUserById(UUID id);

    User findUserByUsername(String username);

    List<User> getAllUsers();

    User updateUser(User user);

    boolean usernameExist(String username);
    
}
