package com.chat.app.chatapp.dto.messages;

import lombok.Builder;

@Builder
public record MessageResponse(
        String id,
        String senderId,
        String senderName,
        String text,
        String timestamp,
        FileDataResponse fileData
) {
}
