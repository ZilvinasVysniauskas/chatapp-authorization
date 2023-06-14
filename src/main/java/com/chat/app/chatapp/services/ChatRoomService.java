package com.chat.app.chatapp.services;

import com.chat.app.chatapp.dto.RoomResponse;
import com.chat.app.chatapp.excpetions.ChatRoomException;
import com.chat.app.chatapp.excpetions.ChatRoomIllegalActionException;
import com.chat.app.chatapp.mapper.ChatRoomMapper;
import com.chat.app.chatapp.model.chat.ChatRoom;
import com.chat.app.chatapp.model.user.User;
import com.chat.app.chatapp.repository.ChatRoomRepository;
import com.chat.app.chatapp.request.RoomRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserService userService) {
        this.chatRoomRepository = chatRoomRepository;
        this.userService = userService;
    }

    public RoomResponse createChatRoom(RoomRequest roomRequest, String ownerId) {
        User owner = userService.findUserById(ownerId);
        List<User> participants = new ArrayList<>(Optional.ofNullable(roomRequest.participants())
                .orElse(Collections.emptyList())
                .stream()
                .map(userService::findUserById)
                .toList());
        participants.add(owner);
        ChatRoom chatRoom = ChatRoom.builder()
                .name(roomRequest.name())
                .description(roomRequest.description())
                .owner(owner)
                .participants(participants)
                .build();
        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);
        return ChatRoomMapper.mapChatRoomToRoomResponse(savedRoom);
    }

    public RoomResponse getChatRoom(String roomId) {
        ChatRoom chatRoom = findChatRoomById(roomId);
        return ChatRoomMapper.mapChatRoomToRoomResponse(chatRoom);
    }



    public RoomResponse addUserToChatRoom(String roomId, String userId) {
        User user = userService.findUserById(userId);
        ChatRoom chatRoom = findChatRoomById(roomId);
        chatRoom.participants().add(user);
        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);
        return ChatRoomMapper.mapChatRoomToRoomResponse(savedRoom);
    }

    public RoomResponse removeUserFromChatRoom(String roomId, String userId, String currentUserId) {
        ChatRoom chatRoom = findChatRoomById(roomId);
        if (!currentUserId.equals(userId) || !chatRoom.owner().getId().equals(userId)) {
            throw new ChatRoomIllegalActionException("Only owner can remove other users from chat room");
        }
        User user = userService.findUserById(userId);
        chatRoom.participants().remove(user);
        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);
        return ChatRoomMapper.mapChatRoomToRoomResponse(savedRoom);
    }

    private ChatRoom findChatRoomById(String id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomException("Chat room not found with id : " + id));
    }
}
