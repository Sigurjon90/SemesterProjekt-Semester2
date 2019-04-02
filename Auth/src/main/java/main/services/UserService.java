package main.services;

import main.domain.User;
import main.domain.UserDTO;
import main.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserRepository userRepository;

    public Optional<UserDTO> findById(String id) {
        Optional user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = modelMapper.map(user.get(), UserDTO.class);
            return Optional.of(userDTO);
        }
        return Optional.empty();
    }

    public void insertUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setId();
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

}
