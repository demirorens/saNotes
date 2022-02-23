package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.BooleanResponse;
import com.sanotes.saNotesWeb.payload.UserResponse;
import com.sanotes.saNotesWeb.security.UserPrincipal;

public interface UserService {

    BooleanResponse checkUsernameAvailability(String username);

    BooleanResponse checkEmailAvailability(String email);

    User addUser(User user);

    User updateUser(User newUser, String username, UserPrincipal currentUser);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);

    ApiResponse giveAdmin(String username);

    ApiResponse removeAdmin(String username);

    UserResponse getUser(String username,  UserPrincipal currentUser);
}
