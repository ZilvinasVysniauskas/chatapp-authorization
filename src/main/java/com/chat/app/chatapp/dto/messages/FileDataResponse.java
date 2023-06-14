package com.chat.app.chatapp.dto.messages;

import lombok.Builder;

@Builder
public record FileDataResponse(
        String fileName,
        String getFileAwsS3Url
) {
}
