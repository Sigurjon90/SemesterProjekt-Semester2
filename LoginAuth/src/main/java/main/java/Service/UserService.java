/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import main.java.Repository.UserRepository;
import main.java.Entity.CreateUserDTO;
import main.java.Entity.User;
import main.java.Entity.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jacobwowkjorgensen
 */
@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        List<UserDTO> usersDTO = new ArrayList();
        for (User user : users) {
            UserDTO tempDTO = modelMapper.map(user, UserDTO.class);
            usersDTO.add(tempDTO);

        }
        return usersDTO;
    }

    public UserDTO createUser(CreateUserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User userCreated = userRepository.CreateUser(user);
        if (userCreated != null) {
            UserDTO createdUserDTO = modelMapper.map(userCreated, UserDTO.class);
            return createdUserDTO;
        }
        return null;
    }

    public UserDTO findUserById(UUID id) {
       
        User userFind = userRepository.findUserById(id);
        if (userFind != null) {
            return modelMapper.map(userFind, UserDTO.class);
        }
        return null;
    }

    public UserDTO findUserByUsername(String username) {
        User userDTO = userRepository.findUserByUsername(username);
        if (userDTO != null) {
            return modelMapper.map(userDTO, UserDTO.class);
        }
        return null;
    }

    public boolean deleteUserById(UUID id) {
        return userRepository.deleteUserById(id);
    }

    public UserDTO updateUser(UserDTO userDTO) {
       
        User user = modelMapper.map(userDTO, User.class);
        User userUpdated = userRepository.updateUser(user);

        if (userUpdated != null) {
            return modelMapper.map(userUpdated, UserDTO.class);
        }
        return null;
    }

    public List<UUID> findCitizensById(UUID id) {
            
        
         List<UUID> citizensList = userRepository.findCitizensById(id);
        if (citizensList != null) {
           return citizensList; 
        }
        return null;
    }

}
