package commonService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class JwtUtils {

    @Autowired
    private JwtConfig jwtConfig;

    private Jws<Claims> getClaims(String jwt) {
        String token = jwt.replace(jwtConfig.getPrefix(), "");
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(token);
    }

    public UUID getUserId(String jwt) {
        Claims claims = getClaims(jwt).getBody();
        return UUID.fromString(claims.getId());
    }

    public List<UUID> getMyCitizens(String jwt) {
        JwsHeader headers = getClaims(jwt).getHeader();
        return (List<UUID>)headers.get("citizens");
    }

}
