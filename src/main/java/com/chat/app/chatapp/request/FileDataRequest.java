package com.chat.app.chatapp.request;

import com.chat.app.chatapp.validators.ValidFileType;
import jakarta.validation.constraints.NotNull;

public record FileDataRequest(
        @NotNull
        String fileName,
        @NotNull @ValidFileType
        String fileType
) {
}
