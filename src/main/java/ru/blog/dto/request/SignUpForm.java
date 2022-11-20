package ru.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.blog.models.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для регистрации")
public class SignUpForm {
    @NotBlank()
    @Length(min = 2, max = 15)
    @Schema(description = "Имя")
    private String firstName;

    @NotNull()
    @Length(min = 1, max = 20)
    @Schema(description = "Фамилия")
    private String lastName;


    @Email(message = "Email не подходит")
    @Schema(description = "Email")
    private String email;

    @NotBlank
    @Length(min = 8, max = 25)
    @Schema(description = "Пароль")
    private String password;

    @NotBlank
    @Schema(description = "Повторный пароль")
    private String repeatPassword;

    @NotBlank
    @Schema(description = "Роль")
    private String role;

    public static SignUpForm from(Account account) {
        return SignUpForm.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .password(account.getPassword())
                .role(account.getRole().name())
                .build();
    }
}
