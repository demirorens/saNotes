package com.sanotes.saNotesWeb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {

    UserDetails loadUserByUserName(String userNameOrEmail) throws UsernameNotFoundException;

    UserDetails loadUserById(Long id);

}
