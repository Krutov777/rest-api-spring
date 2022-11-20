package ru.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blog.models.Author;
import ru.blog.validation.annotations.NotSameNames;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для добавления автора")
@NotSameNames(names = {"firstName", "lastName"}, message = "{names} are same")
public class AddAuthorRequest {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 1, max = 10, message = "минимальный размер имени {min}, максимальный - {max}")
    @Schema(description = "Имя", example = "Александр")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 1, max = 10, message = "минимальный размер имени {min}, максимальный - {max}")
    @Schema(description = "Фамилия", example = "Крутов")
    private String lastName;

    public static AddAuthorRequest from(Author author) {
        return AddAuthorRequest.builder()
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static List<AddAuthorRequest> from(List<Author> authors) {
        return authors.stream().map(AddAuthorRequest::from).collect(Collectors.toList());
    }
}
