package ru.blog.services;

import org.springframework.security.core.Authentication;
import ru.blog.dto.response.AuthorPageResponse;
import ru.blog.dto.response.AuthorResponse;
import ru.blog.dto.request.AddAuthorRequest;
import ru.blog.dto.request.AddPostToFavoritesRequest;
import ru.blog.dto.request.DeletePostFromFavoritesRequest;
import ru.blog.dto.request.UpdateAuthorRequest;


public interface AuthorService {
    AuthorPageResponse getAuthors(int page, Authentication authentication);

    AuthorResponse addAuthor(AddAuthorRequest addAuthorRequest, Authentication authentication);

    AuthorResponse updateAuthor(UpdateAuthorRequest updateAuthorRequest, Authentication authentication);

    void addPostToFavorites(AddPostToFavoritesRequest addPostToFavoritesRequest, Authentication authentication);

    void deletePostFromFavorites(DeletePostFromFavoritesRequest deletePostFromFavoritesRequest, Authentication authentication);
}
