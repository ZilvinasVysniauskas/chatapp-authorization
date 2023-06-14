package com.chat.app.chatapp.services;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
public class AmazonS3Service {

    private final S3Client s3;
    private final S3Presigner presigner;
    private static final String BUCKET_NAME = "chat-app-file-storage";

    public AmazonS3Service(@Value("${amazon.s3.accessKey}") String accessKey,
                           @Value("${amazon.s3.secretKey}") String secretKey) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("AKIATRWDS2WY6NWHEWEM", "8HXHTx8mUmFGKPW4fF5/bEHa9t1APW+dBXQtlv8O");

        s3 = S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        presigner = S3Presigner.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String generatePresignedUrl(String fileId, String contentType) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileId)
                .contentType(contentType)
                .acl("public-read")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(objectRequest)
                .signatureDuration(Duration.ofMinutes(5))
                .build();

        return presigner.presignPutObject(presignRequest).url().toString();
    }

    public String getFileUrl(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(5))
                .build();

        return presigner.presignGetObject(presignRequest).url().toString();
    }

    @PreDestroy
    private void cleanup() {
        presigner.close();
        s3.close();
    }
}
