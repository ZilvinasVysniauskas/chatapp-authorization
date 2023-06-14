package com.chat.app.chatapp.mapper;

import com.chat.app.chatapp.dto.user.UserResponse;
import com.chat.app.chatapp.model.user.User;

public class UserMapper {

    public static UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }
}