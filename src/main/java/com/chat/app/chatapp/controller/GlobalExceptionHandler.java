package com.chat.app.chatapp.controller;

import com.chat.app.chatapp.excpetions.ChatRoomException;
import com.chat.app.chatapp.excpetions.ChatRoomIllegalActionException;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;

@ControllerAdvice()
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @ExceptionHandler({ChatRoomException.class, UsernameNotFoundException.class})
    public ResponseEntity<?> handleChatRoomException(ChatRoomException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(sdf.format(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ChatRoomIllegalActionException.class})
    public ResponseEntity<?> handleChatRoomIllegalActionException(ChatRoomIllegalActionException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .timestamp(sdf.format(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @Builder
    @Data
    public static class ErrorResponse {
        private int status;
        private String message;
        private String timestamp;
    }
}
