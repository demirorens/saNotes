package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesPostgres.service.model.user.User;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.BooleanResponse;
import com.sanotes.saNotesWeb.payload.UserResponse;
import com.sanotes.saNotesWeb.security.CurrentUser;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/isUsernameAvailable")
    public ResponseEntity<BooleanResponse> isUsernameAvailable(@RequestParam(value = "username") String username) {
        BooleanResponse booleanResponse = userService.checkUsernameAvailability(username);
        return new ResponseEntity< >(booleanResponse, HttpStatus.OK);
    }

    @GetMapping("/isEmailAvailable")
    public ResponseEntity<BooleanResponse> isEmailAvailable(@RequestParam(value = "email") String email) {
        BooleanResponse booleanResponse = userService.checkEmailAvailability(email);
        return new ResponseEntity< >(booleanResponse, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<User> addUser(@Valid @RequestBody User user){
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public  ResponseEntity<User> updateUser(@Valid @RequestBody User user,
                                            @PathVariable(value = "username") String username,
                                            @CurrentUser UserPrincipal userPrincipal){
        User newUser = userService.updateUser(user,username,userPrincipal);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
                                            @CurrentUser UserPrincipal userPrincipal){
        ApiResponse  apiResponse = userService.deleteUser(username,userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/giveAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse> giveAdmin(@PathVariable(value = "username") String username){
        ApiResponse apiResponse = userService.giveAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/removeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse> removeAdmin(@PathVariable(value = "username") String username){
        ApiResponse apiResponse = userService.removeAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{username}/getUserItems")
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<UserResponse> getUserItems(@PathVariable(value = "username") String username,
                                                      @CurrentUser UserPrincipal userPrincipal){
        UserResponse UserResponse = userService.getUser(username,userPrincipal);
        return new ResponseEntity<>(UserResponse, HttpStatus.OK);
    }
}
