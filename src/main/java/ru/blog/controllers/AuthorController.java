package ru.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import ru.blog.api.AuthorApi;
import ru.blog.dto.request.AddAuthorRequest;
import ru.blog.dto.request.AddPostToFavoritesRequest;
import ru.blog.dto.request.DeletePostFromFavoritesRequest;
import ru.blog.dto.request.UpdateAuthorRequest;
import ru.blog.dto.response.AuthorPageResponse;
import ru.blog.dto.response.AuthorResponse;
import ru.blog.services.AuthorService;


@RequiredArgsConstructor
@RestController
public class AuthorController implements AuthorApi {

    private final AuthorService authorService;


    @Override
    public ResponseEntity<AuthorPageResponse> getAuthors(int page, Authentication authentication) {
        return ResponseEntity.ok(authorService.getAuthors(page, authentication));
    }

    @Override
    public ResponseEntity<AuthorResponse> addAuthor(AddAuthorRequest addAuthorRequest, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorService.addAuthor(addAuthorRequest, authentication));
    }

    @Override
    public ResponseEntity<AuthorResponse> updateAuthor(UpdateAuthorRequest updateAuthorRequest, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(authorService.updateAuthor(updateAuthorRequest, authentication));
    }

    @Override
    public ResponseEntity<?> addPostToFavorites(AddPostToFavoritesRequest addPostToFavoritesRequest, Authentication authentication) {
        authorService.addPostToFavorites(addPostToFavoritesRequest, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<?> deletePostFromFavorites(DeletePostFromFavoritesRequest deletePostFromFavoritesRequest, Authentication authentication) {
        authorService.deletePostFromFavorites(deletePostFromFavoritesRequest, authentication);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).build();
    }

}
