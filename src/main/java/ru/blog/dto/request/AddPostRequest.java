package ru.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма добавления поста")
public class AddPostRequest {
    @NotBlank(message = "Заголовок поста не может быть пустым")
    @Size(min = 1, max = 255, message = "минимальный размер заголовка поста {min}, максимальный - {max}")
    @Schema(description = "Заголовок поста")
    private String title;

    @NotBlank(message = "Текст поста не может быть пустым")
    @Size(min = 1, max = 255, message = "минимальный размер текста {min}, максимальный - {max}")
    @Schema(description = "Текст поста")
    private String text;

    @NotNull(message = "Идентификатор автора не может быть null")
    @Schema(description = "Идентификатор автора", example = "1")
    private Long authorId;
}

