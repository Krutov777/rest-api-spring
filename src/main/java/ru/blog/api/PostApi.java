package ru.blog.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.blog.dto.request.AddPostRequest;
import ru.blog.dto.response.PostResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Schema(name = "Posts", description = "posts operations")
@RequestMapping("api/posts")
public interface PostApi {
    @Operation(summary = "Получение поста по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = PostResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка при получении поста по id"),
            @ApiResponse(responseCode = "401", description = "Не авторизованы"),
            @ApiResponse(responseCode = "403", description = "Нет прав доступа"),
            @ApiResponse(responseCode = "404", description = "Пост не найден")
    })
    @GetMapping("/{post-id}")
    ResponseEntity<PostResponse> getPost(@PathVariable("post-id") @Valid @NotNull @Schema(description = "Идентификатор поста", example = "1")
                                         Long postId, Authentication authentication);

    @Operation(summary = "Добавление поста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = PostResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка при добавлении поста"),
            @ApiResponse(responseCode = "401", description = "Не авторизованы"),
            @ApiResponse(responseCode = "403", description = "Нет прав доступа"),
            @ApiResponse(responseCode = "404", description = "пост")
    })
    @PostMapping
    ResponseEntity<PostResponse> addPost(@RequestBody @Valid AddPostRequest addPostRequest, Authentication authentication);
}
