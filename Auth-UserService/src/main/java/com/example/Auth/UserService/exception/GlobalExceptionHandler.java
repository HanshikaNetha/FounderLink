package com.example.Auth.UserService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredintialsException.class)
    public ResponseEntity<String> handleInvalidCredintials(InvalidCredintialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedRoleException.class)
    public ResponseEntity<String> handleUnAuthorizedRole(UnauthorizedRoleException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<String> handleInvalidRole(InvalidRoleException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }


    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("You are not authorized to perform this action");
    }

}
