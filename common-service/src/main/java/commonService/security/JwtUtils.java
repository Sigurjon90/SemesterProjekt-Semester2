package commonService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtUtils {

    @Autowired
    private JwtConfig jwtConfig;

    public String getUserId(String jwt) {
        String token = jwt.replace(jwtConfig.getPrefix(), "");
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

        return claims.getId();
    }

}
