package com.chat.app.chatapp.dto.messages;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SavedFileResponse {
    private String fileId;
    private String awsUploadUrl;
}
