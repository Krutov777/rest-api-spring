package ru.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import ru.blog.api.PostApi;
import ru.blog.dto.request.AddPostRequest;
import ru.blog.dto.response.PostResponse;
import ru.blog.services.PostService;


@RequiredArgsConstructor
@RestController
public class PostController implements PostApi {
    private final PostService postService;

    @Override
    public ResponseEntity<PostResponse> getPost(Long postId, Authentication authentication) {
        return ResponseEntity.ok(postService.getPost(postId, authentication));
    }

    @Override
    public ResponseEntity<PostResponse> addPost(AddPostRequest addPostRequest, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.addPost(addPostRequest, authentication));
    }
}
