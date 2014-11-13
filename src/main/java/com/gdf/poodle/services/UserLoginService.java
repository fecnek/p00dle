package com.gdf.poodle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gdf.poodle.repositories.entities.SystemUser;

@Component
public class UserLoginService implements UserDetailsService {
		@Autowired
		SystemUserService systemUserService;

		@Override
		public UserDetails loadUserByUsername(String username)
				throws UsernameNotFoundException {
			
	        Optional<SystemUser> authUser = systemUserService.findUserByUserName(username);
	        if(!authUser.isPresent()) {
	        	throw new UsernameNotFoundException("Uknown user");
	        }
	        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            UserDetails user = new User(username, authUser.get().getPassword(),  auths);

			return user;
		}

}
