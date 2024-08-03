package com.hotelbooking.utils;



import java.io.IOException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//this is abstract class
//and have one abstract method name as dofilterinternal 
@Component
public class JwtFilters extends OncePerRequestFilter{

@Autowired
private JwtUtils jwtutils;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(request.getAttribute("email"));
		String authHeadr=request.getHeader("Authorization");
		System.out.println(authHeadr);
		if(authHeadr!=null&&authHeadr.startsWith("Bearer")) {
			System.out.println("Got bearer token ");
			String token=authHeadr.substring(7);
			//Bearer =7
			//now from this token we get Claims object which is present inside the 
			//jwtutils class which returns claims
			Claims claim=jwtutils.validateToken(token);
			//from claim objec we get username / email
			String email=jwtutils.getUserNameByToken(claim);
			//also get list of authorities 
			List<GrantedAuthority> authorities =jwtutils.getAuthoritiesFromClaims(claim);
			//now wrap the username and authorities in the usernamepasswordauthtoken
			UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email,null,authorities);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		else 
			System.out.println("Request did not contain any token ");
		filterChain.doFilter(request, response);
		//passing the control to the next filter chain
		
		
	}

}

