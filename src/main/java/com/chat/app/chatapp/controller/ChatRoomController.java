package com.chat.app.chatapp.controller;

import com.chat.app.chatapp.dto.RoomResponse;
import com.chat.app.chatapp.model.chat.ChatRoom;
import com.chat.app.chatapp.request.RoomRequest;
import com.chat.app.chatapp.security.CurrentUser;
import com.chat.app.chatapp.security.UserPrincipal;
import com.chat.app.chatapp.services.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RoomResponse> createChatRoom(
            @RequestBody RoomRequest roomRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        RoomResponse chatRoom = chatRoomService.createChatRoom(roomRequest, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoom);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RoomResponse> getChatRoomById(@PathVariable String id) {
        RoomResponse chatRoom = chatRoomService.getChatRoom(id);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/add-user/{roomId}/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RoomResponse> addUserToChatRoom(@PathVariable String roomId, @PathVariable String userId) {
        RoomResponse chatRoom = chatRoomService.addUserToChatRoom(roomId, userId);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/remove-user/{roomId}/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RoomResponse> removeUserFromChatRoom(
            @PathVariable String roomId,
            @PathVariable String userId,
            @CurrentUser UserPrincipal userPrincipal) {
        RoomResponse chatRoom = chatRoomService.removeUserFromChatRoom(roomId, userId, userPrincipal.getId());
        return ResponseEntity.ok(chatRoom);
    }
}
