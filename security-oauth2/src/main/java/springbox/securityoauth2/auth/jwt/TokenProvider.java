package springbox.securityoauth2.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    public static final long ACCESS_TOKEN_EXPIRE = 2 * 60 * 60 * 1000L;      // 2H
    public static final long REFRESH_TOKEN_EXPIRE = 7 * 24 * 60 * 60 * 1000L; // D7

    public static final String BEARER = "Bearer ";

    public static final String ACCESS_TOKEN = "Authorization";
    public static final String REFRESH_TOKEN = "Refresh_Token";


    public static final String ACCESS = "ACCESS";
    public static final String REFRESH = "REFRESH";

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public TokenResponse generateToken(String email) {
        return new TokenResponse(createToken(email, ACCESS), createToken(email, REFRESH));
    }

    public String createToken(String email, String type) {
        Date date = new Date();

        long time = type.equals(ACCESS) ? ACCESS_TOKEN_EXPIRE : REFRESH_TOKEN_EXPIRE;

        return BEARER + Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
