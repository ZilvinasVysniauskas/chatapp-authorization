package com.chat.app.chatapp.mapper;

import com.chat.app.chatapp.dto.RoomResponse;
import com.chat.app.chatapp.model.chat.ChatRoom;

public class ChatRoomMapper {

    public static RoomResponse mapChatRoomToRoomResponse(ChatRoom chatRoom) {
        return RoomResponse.builder()
                .id(chatRoom.id())
                .name(chatRoom.name())
                .owner(UserMapper.mapUserToUserResponse(chatRoom.owner()))
                .description(chatRoom.description())
                .participants(chatRoom.participants().stream().map(UserMapper::mapUserToUserResponse).toList())
                .build();
    }
}
