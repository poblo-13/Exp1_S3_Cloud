package com.duoc.enrollmentservice.service;

import com.duoc.enrollmentservice.model.Asset;
import java.util.List;

public interface AwsService {
    List<String> listFiles(String bucketName);
    Asset downloadFile(String bucketName, String key);
    void uploadFile(String bucketName, String key, byte[] fileBytes, String contentType);
    void deleteFile(String bucketName, String key);
}