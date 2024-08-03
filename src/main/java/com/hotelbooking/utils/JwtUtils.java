package com.hotelbooking.utils;



import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.hotelbooking.customerUserService.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
@Value("${SECRET_KEY}")
	private String jwtSecret;
@Value("${EXP_TIMEOUT}")
private int Exptime;

private Key key;

@PostConstruct
public void init() {
	key=Keys.hmacShaKeyFor(jwtSecret.getBytes());
}

public String generatToken(Authentication authentication) {
	log.info("generate jwt tocken "+authentication);
	CustomUserDetails userprinciple=(CustomUserDetails) authentication.getPrincipal();
	return Jwts.builder()
			.setSubject((userprinciple.getUsername()))
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime()+Exptime))
				.claim("authorities",getAuthoritiesInString(userprinciple.getAuthorities()))
				.signWith(key,SignatureAlgorithm.HS512).compact();
				
			
					
			
}
//this method will give us username 
public String getUserNameByToken(Claims claims) {
	return claims.getSubject();
}
public Claims validateToken(String jwtToken) {
	Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
	return claims;
}

private String getAuthoritiesInString(Collection<? extends GrantedAuthority> authorities) {
	// TODO Auto-generated method stub
	String Authority=authorities.stream().map(authority->authority.getAuthority()).collect(Collectors.joining(","));
	System.out.println(Authority);
	return Authority;
}

public List<GrantedAuthority> getAuthoritiesFromClaims(Claims claims){
	String authstring=(String) claims.get("authorities");
	List<GrantedAuthority> authorities=AuthorityUtils.commaSeparatedStringToAuthorityList(authstring);
	authorities.forEach(System.out::println);
	return authorities;
}


}
