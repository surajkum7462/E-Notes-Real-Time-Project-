package com.suraj.service.impl;

import java.security.Key;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.suraj.entity.User;
import com.suraj.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JWTService {

	private String secretKey = "";

	public JwtServiceImpl() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());
		claims.put("id", user.getId());

		String token = Jwts.builder().claims().add(claims).subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000*60*10)).and().signWith(getKey()).compact();
		return token;
	}

	private Key getKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String extractUserName(String token) {
		// Extracting the username using token for validation
		Claims claims = extractAllClaims(token);

		return claims.getSubject();
	}

	// For understanding that how can i extract info from claims

	public String role(String token) {
		Claims claims = extractAllClaims(token);
		String role = (String) claims.get("role");

		return role;
	}

	private Claims extractAllClaims(String token) {
		Claims claims = Jwts.parser().verifyWith(decryptKey(secretKey)).build().parseSignedClaims(token).getPayload();
		return claims;
	}

	private SecretKey decryptKey(String secretKey2) {
		byte[] decode = Decoders.BASE64.decode(secretKey2);
		// return decoded key in terms of SecretKey
		return Keys.hmacShaKeyFor(decode);
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {

		String username = extractUserName(token);
		Boolean isExpired=isTokenExpired(token);
		if(username.endsWith(userDetails.getUsername()) && !isExpired)
		{
			return true;
		}
		
		
		
		
		return false;
	}

	private Boolean isTokenExpired(String token) {
		Claims claims = extractAllClaims(token);
		Date expiryDate = claims.getExpiration();
		
		// 10th - today -> before  expire (11th)
		
		return expiryDate.before(new Date());
	}

}
