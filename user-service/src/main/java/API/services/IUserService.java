/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import java.util.List;
import java.util.UUID;
import API.entities.CreateUserDTO;
import API.entities.UserDTO;
import API.entities.ValidatedUserDTO;

/**
 *
 * @author jacobwowkjorgensen
 */
public interface IUserService {

    UserDTO createUser(CreateUserDTO userDTO, UUID careCenterId);

    boolean deleteUserById(UUID id);

    List<UUID> findCitizensById(UUID id);

    UserDTO findUserById(UUID id);

    ValidatedUserDTO findUserByUsername(String username);

    List<UserDTO> getAllUsers(UUID careCenterId);

    UserDTO updateUser(UserDTO userDTO);
}
