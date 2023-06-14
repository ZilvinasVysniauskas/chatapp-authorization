package com.chat.app.chatapp.repository;

import com.chat.app.chatapp.model.chat.ChatRoom;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
}