package com.sanotes.web.service;

import com.sanotes.commons.model.user.User;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.BooleanResponse;
import com.sanotes.web.payload.UserResponse;
import com.sanotes.web.security.UserPrincipal;

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
