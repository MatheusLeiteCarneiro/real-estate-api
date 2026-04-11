package com.mlcdev.realestate.exception;

public class ImageCompressingException extends RuntimeException {
    public ImageCompressingException(String message) {
        super(message);
    }
    public ImageCompressingException(String message, Throwable cause) {
        super(message, cause);
    }

}
