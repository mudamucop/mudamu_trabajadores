package com.Mudamu.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Mudamu.model.Medico;
import com.Mudamu.rest.UserRESTClient;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRESTClient userRESTClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		userRESTClient = new UserRESTClient();
		
		Medico appUser = userRESTClient.getUserName(username);
		Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>(); 
		
		UserDetails user = (UserDetails) new User(username,  appUser.getPassword(),grantList);


		return user;
	}
}
