package capstone.dissent.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    // Signing key
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // "Configurable" constants
    private final String ISSUER = "dissent";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(User user) {

        // authorities are roles.
        String authorities = user.getAuthorities().stream()
                .map(i -> i.getAuthority())
                .collect(Collectors.joining(","));

        // Use JJWT classes to build a token.
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                // able to add additional claims -
                // data-points or bits of info about user, api "claims" about user
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public User getUserFromToken(String token) {

        // ensures token starts with "Bearer " (following standard conventions)
        // and token is not null.
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            // Use JJWT classes to read a token.
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String username = jws.getBody().getSubject();
            String authStr = (String) jws.getBody().get("authorities");
            List<GrantedAuthority> authorities = Arrays.stream(authStr.split(","))
                    .map(i -> new SimpleGrantedAuthority(i))
                    .collect(Collectors.toList());

            return new User(username, username, authorities);

        } catch (JwtException e) {
            // JWT failures are modeled as exceptions.
            System.out.println(e);
        }
        return null;
    }
}