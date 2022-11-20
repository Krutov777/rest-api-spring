package ru.blog.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.blog.dto.request.SignUpForm;
import ru.blog.dto.response.SignUpResponse;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Schema(name = "Registration Controller", description = "sign up")
@RequestMapping("api/")
public interface RegistrationApi {

    @Operation(summary = "Регистрация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Аккаунт зарегистрирован",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SignUpResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка при регистрации")
    })
    @PostMapping(value = "/signUp", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpForm form);
}
