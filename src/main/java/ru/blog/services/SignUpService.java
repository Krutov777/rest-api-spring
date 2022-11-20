package ru.blog.services;


import org.springframework.security.core.Authentication;
import ru.blog.dto.request.SignUpForm;
import ru.blog.dto.response.SignUpResponse;


public interface SignUpService {
    SignUpResponse signUp(SignUpForm form);

    void signOut(Authentication authentication);

}
