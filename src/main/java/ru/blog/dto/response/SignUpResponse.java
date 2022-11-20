package ru.blog.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blog.models.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Аккаунт")
public class SignUpResponse {
    @Schema(description = "Роль")
    private Account.Role role;
    @Schema(description = "Состояние", example = "CONFIRMED")
    private Account.State state;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Почта")
    private String email;

    public static SignUpResponse from(Account account) {
        return SignUpResponse.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .role(Account.Role.valueOf(account.getRole().toString()))
                .state(Account.State.valueOf(account.getState().toString()))
                .build();
    }
}