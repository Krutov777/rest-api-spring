package ru.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.blog.dto.response.ValidationExceptionResponse;
import ru.blog.exceptions.AccessException;
import ru.blog.exceptions.BadPasswordException;
import ru.blog.exceptions.EntityNotFoundException;
import ru.blog.exceptions.OccupiedEmailException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationExceptionResponse> handleAccessDeniedException(HttpMessageNotReadableException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationExceptionResponse> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationExceptionResponse.ValidationErrorDto> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {

            String errorMessage = error.getDefaultMessage();
            ValidationExceptionResponse.ValidationErrorDto errorDto = ValidationExceptionResponse.ValidationErrorDto.builder()
                    .message(errorMessage)
                    .build();

            String objectName = null;
            if (error instanceof FieldError) {
                objectName = ((FieldError) error).getField();
            } else if (error instanceof ObjectError) {
                objectName = error.getObjectName();
            }
            errorDto.setObject(objectName);
            errors.add(errorDto);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationExceptionResponse.builder().errors(errors).build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ValidationExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }


    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ValidationExceptionResponse> handleAuthenticationException(AuthenticationException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = AccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ValidationExceptionResponse> handleAccessException(AccessException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationExceptionResponse> handleBadPasswordException(BadPasswordException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .object("Registration")
                                .exception(exception.getClass().getCanonicalName())
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(OccupiedEmailException.class)
    public ResponseEntity<ValidationExceptionResponse> handleOccupiedEmailException(OccupiedEmailException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .object("Registration")
                                .exception(exception.getClass().getCanonicalName())
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ValidationExceptionResponse> handleNotFoundException(EntityNotFoundException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .exception(exception.getClass().getCanonicalName())
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ValidationExceptionResponse> handleAnyException(Exception exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ValidationExceptionResponse.ValidationErrorDto.builder()
                                .exception(exception.getClass().getCanonicalName())
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}

