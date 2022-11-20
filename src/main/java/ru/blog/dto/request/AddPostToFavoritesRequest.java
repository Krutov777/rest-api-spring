package ru.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для добавления поста в избранное автора")
public class AddPostToFavoritesRequest {
    @NotNull(message = "Идентификатор автора не может быть пустым")
    @Schema(description = "Идентификатор автора", example = "1")
    private Long authorId;

    @NotNull(message = "Идентификатор поста не может быть пустым")
    @Schema(description = "Идентификатор поста", example = "1")
    private Long postId;
}