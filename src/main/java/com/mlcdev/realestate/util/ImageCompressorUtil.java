package com.mlcdev.realestate.util;

import com.mlcdev.realestate.exception.ImageCompressingException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

public class ImageCompressorUtil {

    public static byte[] compressImage(MultipartFile image) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(image.getInputStream()).size(1920, 1080).outputQuality(0.85).outputFormat("jpg").toOutputStream(outputStream);
            return outputStream.toByteArray();
        }
        catch (Exception e){
            throw new ImageCompressingException("Error occurred during image compression", e);
        }
    }
}
