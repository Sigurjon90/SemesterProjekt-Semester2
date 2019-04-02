package main.services;

import main.domain.User;
import main.domain.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserDTO> findById(String id);
    void insertUser(UserDTO userDTO);
    List<User> getUsers();
}
