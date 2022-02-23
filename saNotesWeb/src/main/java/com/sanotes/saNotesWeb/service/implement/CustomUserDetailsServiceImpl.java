package com.sanotes.saNotesWeb.service.implement;

import com.sanotes.saNotesPostgres.service.DAO.UserRepository;
import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService,  CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User  user = userRepository.findByUsernameOrEmail(userNameOrEmail,userNameOrEmail)
                .orElseThrow(()->new UsernameNotFoundException(
                        String.format("%s username or email not found",userNameOrEmail)));
        return UserPrincipal.create(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User with %s id not found",id)));
        return UserPrincipal.create(user);
    }
}
