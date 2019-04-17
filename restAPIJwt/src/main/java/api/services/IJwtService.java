package api.services;

import api.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IJwtService {

    String toToken(User user);

    Optional<String> getSubFromToken(String token);

}
