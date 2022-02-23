package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.payload.*;
import com.sanotes.saNotesWeb.security.CurrentUser;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Is username available", description = "Check for availability of username", tags = { "user" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = BooleanResponse.class))) })
    @GetMapping(value = "/isUsernameAvailable", produces = { "application/json", "application/xml" })
    public ResponseEntity<BooleanResponse> isUsernameAvailable(
            @Parameter(description="Username to check availability")
            @RequestParam(value = "username") String username) {
        BooleanResponse booleanResponse = userService.checkUsernameAvailability(username);
        return new ResponseEntity< >(booleanResponse, HttpStatus.OK);
    }

    @Operation(summary = "Is e-Mail available", description = "Check for availability of e-Mail", tags = { "user" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = BooleanResponse.class))) })
    @GetMapping(value = "/isEmailAvailable", produces = { "application/json", "application/xml" })
    public ResponseEntity<BooleanResponse> isEmailAvailable(
            @Parameter(description="eMail to check availability")
            @RequestParam(value = "email") String email) {
        BooleanResponse booleanResponse = userService.checkEmailAvailability(email);
        return new ResponseEntity< >(booleanResponse, HttpStatus.OK);
    }

    @Operation(summary = "Add user",description = "Create new user", security = @SecurityRequirement(name = "bearerAuth"), tags = { "user" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "User created",
                    content = @Content(schema = @Schema(implementation = User.class))) })
    @PostMapping(consumes = { "application/json", "application/xml" })
    @PreAuthorize("hasRole('ADMIN')")

    public  ResponseEntity<User> addUser(
            @Parameter(description="Cannot null or empty.",
                    required=true, schema=@Schema(implementation = User.class))
            @Valid @RequestBody User user){
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update user endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public  ResponseEntity<User> updateUser(@Valid @RequestBody User user,
                                            @PathVariable(value = "username") String username,
                                            @CurrentUser UserPrincipal userPrincipal){
        User newUser = userService.updateUser(user,username,userPrincipal);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public  ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
                                            @CurrentUser UserPrincipal userPrincipal){
        ApiResponse  apiResponse = userService.deleteUser(username,userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/giveAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user as admin endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public  ResponseEntity<ApiResponse> giveAdmin(@PathVariable(value = "username") String username){
        ApiResponse apiResponse = userService.giveAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/removeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove admin role endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public  ResponseEntity<ApiResponse> removeAdmin(@PathVariable(value = "username") String username){
        ApiResponse apiResponse = userService.removeAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{username}/getUserItems")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get user all items(netebook,notes,tags) endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public  ResponseEntity<UserResponse> getUserItems(@PathVariable(value = "username") String username,
                                                      @CurrentUser UserPrincipal userPrincipal){
        UserResponse UserResponse = userService.getUser(username,userPrincipal);
        return new ResponseEntity<>(UserResponse, HttpStatus.OK);
    }
}
