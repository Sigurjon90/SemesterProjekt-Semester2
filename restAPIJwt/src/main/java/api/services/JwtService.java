package api.services;

import api.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class JwtService implements IJwtService {

    private Key secret;
    private int sessionTime;

    @Autowired
    public JwtService(//@Value("${jwt.secret}") String secret,
                      @Value("${jwt.sessionTime}") int sessionTime) {
        this.secret = Keys.secretKeyFor(SignatureAlgorithm.ES512);
        this.sessionTime = sessionTime;
    }

    @Override
    public String toToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .setExpiration(expireTimeFromNow())
                .signWith(this.secret)
                .compact();
    }

    @Override
    public Optional<String> getSubFromToken(String token) {
        return Optional.empty();
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + this.sessionTime * 1000);
    }
}
