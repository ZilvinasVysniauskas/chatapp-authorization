package com.chat.app.chatapp.model;

import com.chat.app.chatapp.types.AuthProvider;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users_table")
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "provider_id")
    private String providerId;
    @Column(name = "provider")
    private AuthProvider provider;

}