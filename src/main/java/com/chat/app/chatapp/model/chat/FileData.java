package com.chat.app.chatapp.model.chat;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Builder
public record FileData(
        @Id String id,
        String contentType,
        String fileName
) { }