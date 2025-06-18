package com.example.user_service.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.user_service.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("password", "Mật khẩu không hợp lệ");

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(401, errors, request.getRequestURI());
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("userName", "Username không tồn tại");
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(401, errors, request.getRequestURI());
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(400, errors, request.getRequestURI());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex,
            HttpServletRequest request) {
        String message = Optional.ofNullable(ex.getMostSpecificCause().getMessage()).orElse("");
        Map<String, String> errors = new HashMap<>();

        Matcher matcher = Pattern.compile("\\((.*?)\\)=\\((.*?)\\)").matcher(message);
        if (matcher.find()) {
            errors.put(matcher.group(1), "Giá trị '" + matcher.group(2) + "' đã tồn tại");
        }

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage() != null ? ex.getMessage() : "Không tìm thấy đối tượng");

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                errors,
                request.getRequestURI());

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}
