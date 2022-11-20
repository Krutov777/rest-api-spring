package ru.blog.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.blog.dto.request.AddAuthorRequest;
import ru.blog.dto.request.AddPostToFavoritesRequest;
import ru.blog.dto.request.DeletePostFromFavoritesRequest;
import ru.blog.dto.request.UpdateAuthorRequest;
import ru.blog.dto.response.AuthorPageResponse;
import ru.blog.dto.response.AuthorResponse;

import javax.validation.Valid;

@Schema(name = "Author Controller", description = "author operations")
@RequestMapping("api/authors")
public interface AuthorApi {

    @Operation(summary = "Получение авторов с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с авторами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = AuthorPageResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка при получении авторов с пагинацией"),
            @ApiResponse(responseCode = "401", description = "Не авторизованы"),
            @ApiResponse(responseCode = "403", description = "Нет прав доступа"),
            @ApiResponse(responseCode = "404", description = "Авторы не найдены")
    })
    @GetMapping
    ResponseEntity<AuthorPageResponse> getAuthors(
            @Parameter(description = "Номер страницы") @RequestParam("page") int page, Authentication authentication);

    @Operation(summary = "Добавление автора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "автор",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = AuthorResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка при добавлении автора"),
            @ApiResponse(responseCode = "401", description = "Не авторизованы"),
            @ApiResponse(responseCode = "403", description = "Нет прав доступа")
    })
    @PostMapping
    ResponseEntity<AuthorResponse> addAuthor(@RequestBody @Valid AddAuthorRequest addAuthorRequest, Authentication authentication);


    @Operation(summary = "Обновление автора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "автор",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = AuthorResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка при обновлении автора"),
            @ApiResponse(responseCode = "401", description = "Не авторизованы"),
            @ApiResponse(responseCode = "403", description = "Нет прав доступа"),
            @ApiResponse(responseCode = "404", description = "Автора не существует")
    })
    @PutMapping
    ResponseEntity<AuthorResponse> updateAuthor(@RequestBody @Valid UpdateAuthorRequest updateAuthorRequest,
                                                Authentication authentication);

    @Operation(summary = "Добавление поста в избранное")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "автор",
                    content = {
                            @Content(mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка при добавлении поста в избранное автора"),
            @ApiResponse(responseCode = "401", description = "Не авторизованы"),
            @ApiResponse(responseCode = "403", description = "Нет прав доступа"),
            @ApiResponse(responseCode = "404", description = "Автора не существует "),
            @ApiResponse(responseCode = "404", description = "Поста не существует")
    })
    @PostMapping("/favorites")
    ResponseEntity<?> addPostToFavorites(@RequestBody @Valid AddPostToFavoritesRequest addPostToFavoritesRequest,
                                         Authentication authentication);

    @Operation(summary = "Удаление поста из избранного")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "автор",
                    content = {
                            @Content(mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка при удалении поста из избранного автора"),
            @ApiResponse(responseCode = "401", description = "Не авторизованы"),
            @ApiResponse(responseCode = "403", description = "Нет прав доступа"),
            @ApiResponse(responseCode = "404", description = "Автора не существует"),
            @ApiResponse(responseCode = "404", description = "Поста не существует")
    })
    @DeleteMapping("/favorites")
    ResponseEntity<?> deletePostFromFavorites(@RequestBody @Valid DeletePostFromFavoritesRequest deletePostFromFavoritesRequest, Authentication authentication);
}
