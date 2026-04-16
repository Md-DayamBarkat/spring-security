package com.app.security.spring_security.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.security.spring_security.entity.AppUser;
import com.app.security.spring_security.enums.Permission;
import com.app.security.spring_security.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final AppUserRepository appUserRepository;

	public CustomUserDetailsService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = loadDomainUser(username);
		return new User(appUser.getUsername(), appUser.getPassword(), buildAuthorities(appUser));
	}

	public AppUser loadDomainUser(String username) {
		return this.appUserRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

	private Set<GrantedAuthority> buildAuthorities(AppUser appUser) {
		Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name()));
		for (Permission permission : appUser.getRole().getPermissions()) {
			authorities.add(new SimpleGrantedAuthority(permission.getAuthority()));
		}
		return authorities;
	}
}
