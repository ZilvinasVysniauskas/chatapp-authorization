package com.chat.app.chatapp.dto;

import com.chat.app.chatapp.dto.user.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomResponse(
        String id,
        String name,
        String description,
        UserResponse owner,
        List<UserResponse> participants
){}