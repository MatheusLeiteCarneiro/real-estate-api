package com.mlcdev.realestate.util;

import com.mlcdev.realestate.exception.ImageCompressingException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageCompressorUtil {

    public static InputStream compressImage(MultipartFile image) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(image.getInputStream()).size(1920, 1080).outputQuality(0.85).outputFormat("jpg").toOutputStream(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (Exception e){
            throw new ImageCompressingException("Error occurred during image compression", e);
        }
    }
}
