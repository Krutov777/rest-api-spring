package ru.blog.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Страница со списком авторов и общее количество таких страниц")
public class AuthorPageResponse {

    @Schema(description = "Авторы")
    private List<AuthorResponse> authors;

    @Schema(description = "Количество доступных страниц")
    private Integer totalPages;
}
