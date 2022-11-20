package ru.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blog.models.Author;
import ru.blog.validation.annotations.NotSameNames;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Автор")
@NotSameNames(names = {"firstName", "lastName"}, message = "{names} are same")
public class UpdateAuthorRequest {
    @NotNull
    @Schema(description = "Идентификатор", example = "1")
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 1, max = 10, message = "минимальный размер имени {min}, максимальный - {max}")
    @Schema(description = "Имя", example = "Александр")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 1, max = 10, message = "минимальный размер имени {min}, максимальный - {max}")
    @Schema(description = "Фамилия", example = "Крутов")
    private String lastName;

    public static UpdateAuthorRequest from(Author author) {
        return UpdateAuthorRequest.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static List<UpdateAuthorRequest> from(List<Author> authors) {
        return authors.stream().map(UpdateAuthorRequest::from).collect(Collectors.toList());
    }
}