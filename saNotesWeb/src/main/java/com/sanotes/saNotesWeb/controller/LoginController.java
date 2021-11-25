package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesPostgres.service.model.user.User;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.JwtAuthenticationResponse;
import com.sanotes.saNotesWeb.payload.LoginRequest;
import com.sanotes.saNotesWeb.payload.SignUpRequest;
import com.sanotes.saNotesWeb.security.JwtTokenProvider;
import com.sanotes.saNotesWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService  userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signupUser(@Valid @RequestBody SignUpRequest signUpRequest){
        String firstName = signUpRequest.getFirstname();
        String lastName = signUpRequest.getLastname();
        String username = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        User user = new User(firstName, lastName, username,password, email);

        User result = userService.addUser(user);

        URI  uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/{userId}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(new ApiResponse(Boolean.TRUE,"User signed up successfully."));
    }

}
