package com.novi.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Schema(description = "Реквест для создания пользователя")
public class CreateUserRequest {

    @Schema(description = "Имя")
    @NotBlank
    //@Mandatory - add customization?
    @Size(min = 1, max = 50)
    private String firstName;

    @Schema(description = "Фамилия")
    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

    @Schema(description = "Отчество")
    @Size(min = 1, max = 100)
    private String middleName;

    @Schema(description = "Номер телефона")
    @Size(min = 1, max = 20)
    private String phoneNumber;

    @Schema(description = "E-mail")
    @NotBlank
    @Email
    @Size(min = 1, max = 64)
    private String email;

    @Schema(description = "Дата рождения")
    @NotBlank
    private String birthday;

    @Schema(description = "Логин")
    private String userLogin;

    @Schema(description = "Пароль")
    //@NotBlank
    @Size(min = 1, max = 256)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$", message = "Password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
