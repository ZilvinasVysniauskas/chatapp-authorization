package com.chat.app.chatapp.mapper;

import com.chat.app.chatapp.dto.messages.FileDataResponse;
import com.chat.app.chatapp.dto.messages.MessageResponse;
import com.chat.app.chatapp.model.chat.Message;

public class MessageMapper {

    public static MessageResponse messageToMessageResponse(Message message, String awsUploadUrl) {
        var messageResponseBuilder = MessageResponse.builder()
                .id(message.id())
                .senderId(message.sender().getId())
                .senderName(message.sender().getName())
                .text(message.text())
                .timestamp(message.timestamp().toString());
        if (message.fileData() != null) {
            messageResponseBuilder.fileData(FileDataResponse.builder()
                    .fileName(message.fileData().fileName())
                    .getFileAwsS3Url(awsUploadUrl)
                    .build());
        }
        return messageResponseBuilder.build();
    }
}
