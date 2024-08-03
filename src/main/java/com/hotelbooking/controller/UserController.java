package com.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.dto.LoginDto;
import com.hotelbooking.dto.ResponseDto;
import com.hotelbooking.entity.User;
import com.hotelbooking.services.IUserservice;
import com.hotelbooking.utils.JwtUtils;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private IUserservice userservice;
	@Autowired
	private AuthenticationManager authmanager;
	@Autowired
	private JwtUtils utils;
@PostMapping("/signup")
public ResponseEntity<?> signup(@RequestBody User user){
	User newuser=userservice.signUp(user);
	if(newuser!=null)
	return ResponseEntity.ok(newuser);
	return null;	
}
@GetMapping("/home")
public ResponseEntity<?> home(){
	return ResponseEntity.ok("welcome");
}
@PostMapping("/signin")
public ResponseEntity<?> signin(@RequestBody LoginDto logindto)
{
	System.out.println("in signin "+logindto.getEmail()+" "+logindto.getPassword());
	Authentication principal=authmanager.authenticate(new UsernamePasswordAuthenticationToken(logindto.getEmail(), logindto.getPassword()));
	String jwttoken=utils.generatToken(principal);
	return ResponseEntity.ok(new ResponseDto(jwttoken,logindto.getEmail()));
}
@GetMapping("/admin")
public ResponseEntity<?> admin(){
	return ResponseEntity.ok("Hello Admin");
}
@GetMapping("/guest")
public ResponseEntity<?> guest(){
	return ResponseEntity.ok("Hello Guest ");
}
}
