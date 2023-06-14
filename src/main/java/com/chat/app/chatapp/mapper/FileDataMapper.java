package com.chat.app.chatapp.mapper;

import com.chat.app.chatapp.dto.messages.SavedFileResponse;
import com.chat.app.chatapp.model.chat.FileData;
import com.chat.app.chatapp.request.FileDataRequest;

public class FileDataMapper {

    public static FileData fileDataRequestToFileData(FileDataRequest fileDataRequest) {
        return FileData.builder()
                .fileName(fileDataRequest.fileName())
                .contentType(fileDataRequest.fileType())
                .build();
    }

    public static SavedFileResponse FileDataToSavedFileResponse(FileData fileData, String awsUploadUrl) {
        return SavedFileResponse.builder()
                .fileId(fileData.id())
                .awsUploadUrl(awsUploadUrl)
                .build();
    }
}
