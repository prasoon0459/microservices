package com.example.authApp.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.authApp.domain.User;
import com.example.authApp.security.InvalidJwtAuthException;
import com.example.authApp.security.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	@Autowired JwtProperties jwtProperties;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    private static PrivateKey secretPrivateKey;
    private PublicKey secretPublicKey;

    @PostConstruct
    protected void init() {
        secretPublicKey=jwtProperties.getSecretPublicKey();
        secretPrivateKey=jwtProperties.getSecretPrivateKey();
    }
       
    
    public String createToken(User user) {
    	
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("name",user.getName());
        claims.put("roles", user.getRoles());
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder()
        	.setHeaderParam("type","jwt")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.RS256, secretPrivateKey)
            .compact();
    }
	public String createRefreshToken(User user) {
	    	
	        Claims claims = Jwts.claims().setSubject(user.getUsername());
	        Date now = new Date();
	        Date validity = new Date(now.getTime() + jwtProperties.getRefreshValidityInMs());
	
	        return Jwts.builder()
	        	.setHeaderParam("type","jwt")
	            .setClaims(claims)
	            .setIssuedAt(now)
	            .setExpiration(validity)
	            .signWith(SignatureAlgorithm.HS256,jwtProperties.getRefreshSecret())
	            .compact();
	}

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretPublicKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String getUsernamefromRefresh(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getRefreshSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretPublicKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
            	return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthException("Expired or invalid JWT token");
        }
    }
    public boolean validateRefreshToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.getRefreshSecret()).parseClaimsJws(token);System.out.println('g');
            if (claims.getBody().getExpiration().before(new Date())) {
            	return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
        	throw new InvalidJwtAuthException("Expired or invalid JWT token");
        }
    }

}
