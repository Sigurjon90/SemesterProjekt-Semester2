/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import API.entities.CreateUserDTO;
import API.entities.User;
import API.entities.UserDTO;
import API.entities.ValidatedUserDTO;
import API.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jacobwowkjorgensen
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers(UUID careCenterId) {
        List<User> users = userRepository.getAllUsers(careCenterId);
        List<UserDTO> usersDTO = new ArrayList();
        for (User user : users) {
            UserDTO tempDTO = modelMapper.map(user, UserDTO.class);
            usersDTO.add(tempDTO);
        }
        return usersDTO;
    }

    @Override
    public UserDTO createUser(CreateUserDTO userDTO, UUID careCenterId) {
        User user = modelMapper.map(userDTO, User.class);
        User userCreated = userRepository.CreateUser(user, careCenterId);
        if (userCreated != null) {
            return modelMapper.map(userCreated, UserDTO.class);
        }
        return null;
    }

    @Override
    public UserDTO findUserById(UUID id) {
        User userFind = userRepository.findUserById(id);
        if (userFind != null) {
            return modelMapper.map(userFind, UserDTO.class);
        }
        return null;
    }

    @Override
    public ValidatedUserDTO findUserByUsername(String username) {
        User userDTO = userRepository.findUserByUsername(username);
        if (userDTO != null) {
            return modelMapper.map(userDTO, ValidatedUserDTO.class);
        }
        return null;
    }

    @Override
    public boolean deleteUserById(UUID id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User userUpdated = userRepository.updateUser(user);

        if (userUpdated != null) {
            return modelMapper.map(userUpdated, UserDTO.class);
        }
        return null;
    }

    @Override
    public List<UUID> findCitizensById(UUID id) {
        List<UUID> citizensList = userRepository.findCitizensById(id);
        if (citizensList != null) {
           return citizensList; 
        }
        return null;
    }

}
