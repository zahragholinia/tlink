package ir.tinyLink.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@Service
public class JwtService {
    private static final String SECRET_KEY = "V9uit18wKk0i8D2WTqBmSxtZ5104fRoFAF3ds6iwDf/IZbdtREo29ngCEzAPbX2Lq18NcLLJpcQV3aEU11FBjTgQUpr0kRORTcw3TsEKzR5bjyhb9vm47HRVqVSpAs5IsmuIP2ppXmGT8PvbiadHpiiwKGQIzd8HnA1PmvXsMs5fBp4alGhX66ovh83EEde4GyL/gl+zE8PCsOtcivJW05mtcxagXNn6Vb6KTIaKyXIDgyj/M/PFjqyZLiZWgl/aAThAjbnFm9WUkCwshS/aw3/Y0ClvJU7qNKpadXh6U2VCsEVZxaxgvwa6+FoZkz+3fWiw36AqRuUCsTES5eszEDLrcMS+G/EMCEbdGYmk9KY=";

    public String extractUsername(String token) {
        return extractClain(token, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);


    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClain(token, Claims::getExpiration);
    }

    public <T> T extractClain(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
