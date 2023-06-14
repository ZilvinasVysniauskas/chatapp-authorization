package com.chat.app.chatapp.services;

import com.chat.app.chatapp.dto.messages.SavedFileResponse;
import com.chat.app.chatapp.mapper.FileDataMapper;
import com.chat.app.chatapp.model.chat.FileData;
import com.chat.app.chatapp.repository.FileRepository;
import com.chat.app.chatapp.request.FileDataRequest;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final AmazonS3Service amazonS3Service;

    public FileService(FileRepository fileRepository, AmazonS3Service amazonS3Service) {
        this.fileRepository = fileRepository;
        this.amazonS3Service = amazonS3Service;
    }


    public SavedFileResponse saveFile(FileDataRequest fileData) {
        FileData file = FileDataMapper.fileDataRequestToFileData(fileData);
        FileData savedFileData = fileRepository.save(file);
        String url = amazonS3Service.generatePresignedUrl(savedFileData.id(), fileData.fileType());
        return FileDataMapper.FileDataToSavedFileResponse(savedFileData, url);
    }
}
