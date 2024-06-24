package com.example.services;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {
    private final MinioClient minioClient;


    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void createBucket(String bucketName) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании bucket" + e.getMessage());
        }
    }

    public String getObjectUrl(String objectName){
        String bucketName = "avatars";

        if (objectName == null) {
            return null;
        }

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(2, TimeUnit.HOURS)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении ссылки" + e.getMessage());
        }
    }

    public String uploadFileAndGetObjectUrl(MultipartFile file) {
        String bucketName = "avatars";
        String objectName = file.getOriginalFilename();

        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке файла", e);
        }
    }
}
