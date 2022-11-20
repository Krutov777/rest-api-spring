package ru.blog.services;

import org.springframework.security.core.Authentication;
import ru.blog.dto.response.PostResponse;
import ru.blog.dto.request.AddPostRequest;


public interface PostService {
    PostResponse getPost(Long postId, Authentication authentication);

    PostResponse addPost(AddPostRequest addPostRequest, Authentication authentication);

}
