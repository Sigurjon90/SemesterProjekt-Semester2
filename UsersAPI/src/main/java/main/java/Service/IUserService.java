/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Service;

import java.util.List;
import java.util.UUID;
import main.java.Entity.CreateUserDTO;
import main.java.Entity.UserDTO;

/**
 *
 * @author jacobwowkjorgensen
 */
public interface IUserService {

    UserDTO createUser(CreateUserDTO userDTO);

    boolean deleteUserById(UUID id);

    List<UUID> findCitizensById(UUID id);

    UserDTO findUserById(UUID id);

    UserDTO findUserByUsername(String username);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO);
    
}
