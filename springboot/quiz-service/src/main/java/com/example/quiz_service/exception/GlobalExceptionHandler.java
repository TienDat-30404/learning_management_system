package com.example.quiz_service.exception;



import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.quiz_service.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

 

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
        } else {
            errors.put("error", "Tạo thất bại");
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

