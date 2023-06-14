package com.chat.app.chatapp.repository;

import com.chat.app.chatapp.model.chat.FileData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<FileData, String> {
}
