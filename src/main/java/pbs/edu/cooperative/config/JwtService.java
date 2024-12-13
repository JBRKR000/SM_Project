package pbs.edu.cooperative.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import pbs.edu.cooperative.User.User;

@Service
public class JwtService {

    private static final String SECRET_KEY = "75238602958ee930c4edf92b6e052afbde9f1ede634a0841532f77d0449415024c7630b725772a7c2265bc6110909e38ce7eac25e39c84b161f266dd80904661396858917818677903d0a219569e6bfbb11b0df9372a460968fbb07d6629607dbe60879079b0127c21d2b770d010fb649d5b6bd380f24a348453217cea957f86f02cd89631f4afd440c6d51b26924eb2eedb4ede5ff0a25bc0f9122b5b241e3267b6eb76b079545e411e1bac1d8b66f47d3be236655eedb993b810e93eaed5b4cb2bbd81b4f976dbbf0459cab1789b1fb8278544ce6cefecc43939118385ced377048c3d6dd2262e8b070d3e21406061477730224e0b9dcfd00af355f1eed7d5";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", ((User) userDetails).getRole().name()); // Dodanie roli do tokenu
        if(!((User) userDetails).getRole().name().equals("ADMIN")){ //siema
            claims.put("tenant_id", ((User) userDetails).getTenant().getTenantId()); //Dodanie tenant_id do tokenu
        }
        return generateToken(claims, userDetails);
    }


    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60* 24 * 100))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public int extractTenantIdFromToken(String token) {
        return (Integer) extractClaim(token, claims -> claims.get("tenant_id"));
    }

}
