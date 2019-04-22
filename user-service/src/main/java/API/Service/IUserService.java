/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Service;

import java.util.List;
import java.util.UUID;
import API.Entity.CreateUserDTO;
import API.Entity.UserDTO;
import API.Entity.ValidatedUserDTO;

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

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO);
}
