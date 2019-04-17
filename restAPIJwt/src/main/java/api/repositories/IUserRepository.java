package api.repositories;

import api.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository {

    void save(User user);

    Optional<User> findById(String id);

    List<User> getUsers();
}

