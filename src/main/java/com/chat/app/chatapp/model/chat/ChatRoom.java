package com.chat.app.chatapp.model.chat;

import com.chat.app.chatapp.model.user.User;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chatrooms")
@Builder
public record ChatRoom(
        @Id String id,
        String name,
        String description,
        @DBRef User owner,
        @DBRef List<User> participants,
        @DBRef(lazy = true) List<Message> messages
) {}