package ru.blog.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blog.models.Author;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Автор")
public class AuthorResponse {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;

    @Schema(description = "Имя", example = "Александр")
    private String firstName;

    @Schema(description = "Фамилия", example = "Крутов")
    private String lastName;

    public static AuthorResponse from(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static List<AuthorResponse> from(List<Author> authors) {
        return authors.stream().map(AuthorResponse::from).collect(Collectors.toList());
    }
}
