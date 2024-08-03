package com.hotelbooking.customerUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hotelbooking.entity.User;
import com.hotelbooking.repo.UserDao;

@Component
public class CustomUserDetailService implements UserDetailsService {
@Autowired
private UserDao userrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user=userrepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Invalid email"));
	
		// TODO Auto-generated method stub
		return new CustomUserDetails(user);
	}

}
