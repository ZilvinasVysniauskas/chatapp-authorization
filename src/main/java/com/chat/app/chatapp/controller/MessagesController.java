package com.chat.app.chatapp.controller;

import com.chat.app.chatapp.dto.messages.MessageResponse;
import com.chat.app.chatapp.dto.messages.SavedFileResponse;
import com.chat.app.chatapp.request.FileDataRequest;
import com.chat.app.chatapp.services.FileService;
import com.chat.app.chatapp.services.MessagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private final FileService fileService;
    private final MessagesService messagesService;

    public MessagesController(FileService fileService, MessagesService messagesService) {
        this.fileService = fileService;
        this.messagesService = messagesService;
    }

    @PostMapping("/save-file")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SavedFileResponse> saveFile(@RequestBody FileDataRequest fileData) {
        return new ResponseEntity<>(fileService.saveFile(fileData), HttpStatus.CREATED);
    }

    @GetMapping("/messages/{roomId}/{offset}/{limit}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<MessageResponse>> getChatRoomMessages(
            @PathVariable String roomId,
            @PathVariable int offset,
            @PathVariable int limit) {
        return ResponseEntity.ok(messagesService.getMessagesPage(roomId, offset, limit));
    }
}
