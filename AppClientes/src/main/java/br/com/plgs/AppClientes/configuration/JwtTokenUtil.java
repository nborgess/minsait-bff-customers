package br.com.plgs.AppClientes.configuration;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
 
@Component
public class JwtTokenUtil {

	private String secret = "umSegredoMuitoLongoQueTemMaisDe256BitsParaSerSeguroComHMACSHA";
	private long validityM = 3600000; 
	
	public Map<String, Object> createToken(String username) {
	    Date now = new Date();
	    Date validity = new Date(now.getTime() + validityM);

	    byte[] apiKeySecretByte = Base64.getEncoder().encode(secret.getBytes());
	    Key secretKey = Keys.hmacShaKeyFor(apiKeySecretByte);

	    String token = Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(now)
	            .setExpiration(validity)
	            .signWith(secretKey)
	            .compact();

	    Map<String, Object> tokenResponse = new HashMap<>();
	    tokenResponse.put("token", token);
	    tokenResponse.put("expires_in", validityM / 1000);

	    return tokenResponse;
	}
	
	public boolean validateToken(String token) {
		try {
			byte[] apiKeySecretyByte = Base64.getEncoder().encode(secret.getBytes());
			Key secretKey = Keys.hmacShaKeyFor(apiKeySecretyByte);
			
			Jws<Claims> claims = Jwts.parser().setSigningKey(apiKeySecretyByte)
				.parseClaimsJws(token);
			
			return !claims.getBody().getExpiration().before(new Date());
			
		}catch (Exception e) {
			return false;
		}
	}
	
	public String getUsernameFromToken(String token) {
		try {
			byte[] apiKeySecretyByte = Base64.getEncoder().encode(secret.getBytes());
			Key secretKey = Keys.hmacShaKeyFor(apiKeySecretyByte);
			
			Jws<Claims> claims = Jwts.parser().setSigningKey(apiKeySecretyByte)
				.parseClaimsJws(token);
			
			return claims.getBody().getSubject();
			
		}catch (Exception e) {
			return "";
		}
	}
}