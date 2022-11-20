package ru.blog.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blog.models.Post;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostResponse {
    @Schema(description = "Идентификатор поста", example = "1")
    private Long id;
    @Schema(description = "Дата создания", example = "1")
    private String createdAt;
    @Schema(description = "Заголовок поста")
    private String title;
    @Schema(description = "Текст поста")
    private String text;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .createdAt(post.getCreatedAt().toString())
                .text(post.getText())
                .title(post.getTitle())
                .build();
    }

    public static List<PostResponse> from(List<Post> posts) {
        return posts.stream().map(PostResponse::from).collect(Collectors.toList());
    }
}
