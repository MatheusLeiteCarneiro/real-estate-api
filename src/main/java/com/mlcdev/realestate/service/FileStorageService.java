package com.mlcdev.realestate.service;


import java.io.InputStream;
import java.util.Map;

public interface FileStorageService {

    Map<String , String> uploadFile(InputStream file, String folderName);

    void deleteFile(String fileIdentifier);
}
