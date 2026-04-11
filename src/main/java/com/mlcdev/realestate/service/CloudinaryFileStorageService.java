package com.mlcdev.realestate.service;

import com.cloudinary.Cloudinary;
import com.mlcdev.realestate.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CloudinaryFileStorageService implements FileStorageService{

    private final Cloudinary cloudinary;

    public CloudinaryFileStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public Map<String, String> uploadFile(MultipartFile file, String folderName) {
        try {
            Map<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            log.info("Starting upload of the file into the folder: {}",folderName);

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader().upload(file.getInputStream(), options);

            log.info("File successfully uploaded");
            String publicId = (String) uploadedFile.get("public_id");
            String url = cloudinary.url().secure(true).generate(publicId);
            Map<String, String> fileInformation = new HashMap<>();
            fileInformation.put("url" , url);
            fileInformation.put("fileIdentifier", publicId);
            return fileInformation;
        }
        catch (Exception e){
            throw new FileStorageException("An error occurred on the file upload", e);
        }
    }

    @Override
    public void deleteFile(String fileIdentifier) {
        try {
            cloudinary.uploader().destroy(fileIdentifier, Map.of());
        } catch (Exception e) {
            throw new FileStorageException("An error occurred on the deletion of the file", e);
        }
    }
}
