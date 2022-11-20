package ru.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.blog.api.RegistrationApi;
import ru.blog.dto.request.SignUpForm;
import ru.blog.dto.response.SignUpResponse;
import ru.blog.services.SignUpService;


@RequiredArgsConstructor
@RestController
public class RegistrationController implements RegistrationApi {
    private final SignUpService signUpService;

    @Override
    public ResponseEntity<SignUpResponse> signUp(SignUpForm form) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpService.signUp(form));
    }
}
