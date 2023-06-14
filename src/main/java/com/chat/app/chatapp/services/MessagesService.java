package com.chat.app.chatapp.services;

import com.chat.app.chatapp.dto.messages.MessageResponse;
import com.chat.app.chatapp.excpetions.ChatRoomException;
import com.chat.app.chatapp.mapper.MessageMapper;
import com.chat.app.chatapp.model.chat.ChatRoom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesService {

    private final MongoTemplate mongoTemplate;
    private final AmazonS3Service amazonS3Service;

    public MessagesService(MongoTemplate mongoTemplate, AmazonS3Service amazonS3Service) {
        this.mongoTemplate = mongoTemplate;
        this.amazonS3Service = amazonS3Service;
    }

    public List<MessageResponse> getMessagesPage(String roomId, int limit, int offset) {
        // Define the query
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(roomId));
        query.fields().include("messages");
        query.with(Sort.by(Sort.Direction.DESC, "messages.timestamp"));
        query.with(PageRequest.of(offset, limit));
        ChatRoom chatRoom = mongoTemplate.findOne(query, ChatRoom.class);
        if (chatRoom == null) {
            throw new ChatRoomException("Chat room not found with id: " + roomId);
        }
        if (chatRoom.messages() == null) {
            throw new ChatRoomException("No messages for room with id: " + roomId + " and offset: " + offset + " and limit: " + limit);
        }
        return chatRoom.messages().stream().map(
                        message -> {
                            String fileUrl = null;
                            if (message.fileData() != null) {
                                fileUrl = amazonS3Service.getFileUrl(message.fileData().id());
                            }
                            return MessageMapper.messageToMessageResponse(message, fileUrl);
                        }
                ).toList();
    }
}
