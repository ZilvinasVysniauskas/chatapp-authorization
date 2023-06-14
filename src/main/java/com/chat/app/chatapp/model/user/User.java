package com.chat.app.chatapp.model.user;

import com.chat.app.chatapp.types.AuthProvider;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
@Builder
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String providerId;
    private AuthProvider provider;
}