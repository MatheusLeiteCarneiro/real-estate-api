package com.mlcdev.realestate.exception.handler;

import com.mlcdev.realestate.exception.*;
import com.mlcdev.realestate.exception.response.ApiError;
import com.mlcdev.realestate.exception.response.ValidationApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError error = new ApiError(status.value(), exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError error = new ApiError(status.value(), exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(BusinessRuleException exception, HttpServletRequest request){
        ApiError error = new ApiError(422, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(422).body(error);
    }

    @ExceptionHandler(ResourceMismatchException.class)
    public ResponseEntity<ApiError> handleMismatch(ResourceMismatchException exception, HttpServletRequest request){
        ApiError error = new ApiError(422, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(422).body(error);
    }

    @ExceptionHandler(EmptyResourceException.class)
    public ResponseEntity<ApiError> handleEmptyResource(EmptyResourceException exception, HttpServletRequest request){
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiError> handleFileStorage(FileStorageException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError error = new ApiError(status.value(), exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationApiError> handleValidation(MethodArgumentNotValidException exception, HttpServletRequest request){
        ValidationApiError error = new ValidationApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed", request.getRequestURI());
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccess(HttpServletRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError error = new ApiError(status.value(), "Access Denied", request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError> handleAuthorization(HttpServletRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError error = new ApiError(status.value(), "Insufficient Permission", request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(HttpServletRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError error = new ApiError(status.value(), "An unexpected error occurred", request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}
