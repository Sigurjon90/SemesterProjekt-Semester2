package commonService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtils {
    
    private static JwtConfig jwtConfig;

    public JwtUtils() {
        jwtConfig = new JwtConfig();
    }

    public static String getUserId(String jwt) {
        String token = jwt.replace(jwtConfig.getPrefix(), "");
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

        return claims.getId();
    }

}
