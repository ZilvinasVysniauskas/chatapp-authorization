package com.chat.app.chatapp.excpetions;

public class ChatRoomIllegalActionException extends RuntimeException {
    public ChatRoomIllegalActionException(String message) {
        super(message);
    }
}
