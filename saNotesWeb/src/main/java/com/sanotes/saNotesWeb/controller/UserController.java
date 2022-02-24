package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.payload.*;
import com.sanotes.saNotesWeb.security.CurrentUser;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "user", description = "the User API")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Is username available",
            description = "Check for availability of username",
            tags = {"user"})
    @GetMapping("/isUsernameAvailable")
    public ResponseEntity<BooleanResponse> isUsernameAvailable(
            @Parameter(description = "Username to check availability", required = true)
            @RequestParam(value = "username") String username) {
        BooleanResponse booleanResponse = userService.checkUsernameAvailability(username);
        return new ResponseEntity<>(booleanResponse, HttpStatus.OK);
    }

    @Operation(summary = "Is e-Mail available",
            description = "Check for availability of e-Mail",
            tags = {"user"})
    @GetMapping("/isEmailAvailable")
    public ResponseEntity<BooleanResponse> isEmailAvailable(
            @Parameter(description = "e-Mail to check availability", required = true)
            @RequestParam(value = "email") String email) {
        BooleanResponse booleanResponse = userService.checkEmailAvailability(email);
        return new ResponseEntity<>(booleanResponse, HttpStatus.OK);
    }

    @Operation(summary = "Add user",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"user"})
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(
            @Parameter(description = "User parameters", required = true)
            @Valid @RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @Operation(summary = "Update user",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"user"})
    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "Firstname, lastname and Password are updateable", required = true)
            @Valid @RequestBody User user,
            @Parameter(description = "Username", required = true)
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal userPrincipal) {
        User newUser = userService.updateUser(user, username, userPrincipal);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"user"})
    public ResponseEntity<ApiResponse> deleteUser(
            @Parameter(description = "Username", required = true)
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal userPrincipal) {
        ApiResponse apiResponse = userService.deleteUser(username, userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/giveAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user as admin",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"user"})
    public ResponseEntity<ApiResponse> giveAdmin(
            @Parameter(description = "Username", required = true)
            @PathVariable(value = "username") String username) {
        ApiResponse apiResponse = userService.giveAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/removeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove admin role",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"user"})
    public ResponseEntity<ApiResponse> removeAdmin(
            @Parameter(description = "Username", required = true)
            @PathVariable(value = "username") String username) {
        ApiResponse apiResponse = userService.removeAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{username}/getUserItems")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get user all items(notebooks, notes, tags)",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"user"})
    public ResponseEntity<UserResponse> getUserItems(
            @Parameter(description = "Username", required = true)
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal userPrincipal) {
        UserResponse UserResponse = userService.getUser(username, userPrincipal);
        return new ResponseEntity<>(UserResponse, HttpStatus.OK);
    }
}
