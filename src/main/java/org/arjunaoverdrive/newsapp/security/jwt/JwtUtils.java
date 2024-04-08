package org.arjunaoverdrive.newsapp.security.jwt;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.newsapp.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.tokenTTL}")
    private Duration tokenTTL;

    public String generateJwtToken(AppUserDetails appUserDetails){
        return generateTokenFromUsername(appUserDetails.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenTTL.toMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getSubject();
            return true;
        } catch (SignatureException e){
            log.error("Invalid signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("Token is not supported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("Claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
